package cn.devicelinks.service.device;

import cn.devicelinks.api.model.request.*;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.framework.jdbc.PaginationQueryConverter;
import cn.devicelinks.framework.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.framework.common.*;
import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.api.model.dto.AttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeDesiredDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeDesiredRepository;
import cn.devicelinks.service.attribute.AttributeService;
import cn.devicelinks.service.product.FunctionModuleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeDesired.DEVICE_ATTRIBUTE_DESIRED;

/**
 * 设备期望属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeDesiredServiceImpl extends BaseServiceImpl<DeviceAttributeDesired, String, DeviceAttributeDesiredRepository>
        implements DeviceAttributeDesiredService {

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private DeviceShadowService deviceShadowService;

    public DeviceAttributeDesiredServiceImpl(DeviceAttributeDesiredRepository repository) {
        super(repository);
    }

    @Override
    public DeviceAttributeDesired selectByIdentifier(String deviceId, String moduleId, String identifier) {
        // @formatter:off
        return this.repository.selectOne(
                DEVICE_ATTRIBUTE_DESIRED.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE_DESIRED.MODULE_ID.eq(moduleId),
                DEVICE_ATTRIBUTE_DESIRED.IDENTIFIER.eq(identifier),
                DEVICE_ATTRIBUTE_DESIRED.DELETED.eq(Boolean.FALSE));
        // @formatter:on
    }

    @Override
    public PageResult<DeviceAttributeDesiredDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public DeviceAttributeDesired addDesiredAttribute(Device device, String moduleId, AddDeviceDesiredAttributeRequest request) {
        // validate
        AttributeKnowType knowType = AttributeKnowType.valueOf(request.getKnowType());

        FunctionModule functionModule = this.functionModuleService.selectById(moduleId);
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, moduleId);
        }
        if (!functionModule.getProductId().equals(device.getProductId())) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_BELONG_PRODUCT, functionModule.getIdentifier(), device.getProductId());
        }

        Attribute attribute = this.validate(knowType, request.getDesiredValue(),
                request.getAttributeId(), request.getIdentifier(), request.getDataType());

        if (AttributeKnowType.Known == knowType) {
            if (!attribute.getProductId().equals(functionModule.getProductId())) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_BELONG_PRODUCT, attribute.getIdentifier(), functionModule.getProductId());
            }
            if (!attribute.getModuleId().equals(functionModule.getId())) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_BELONG_FUNCTION_MODULE, attribute.getIdentifier(), functionModule.getIdentifier());
            }
        }


        String identifier = AttributeKnowType.Known == knowType ? attribute.getIdentifier() : request.getIdentifier();
        DeviceAttributeDesired desiredAttribute = this.selectByIdentifier(device.getId(), moduleId, identifier);
        // insert
        if (desiredAttribute == null) {
            // @formatter:off
            desiredAttribute = new DeviceAttributeDesired()
                    .setDeviceId(device.getId())
                    .setModuleId(moduleId)
                    .setVersion(Constants.ONE)
                    .setDesiredValue(request.getDesiredValue())
                    .setLastUpdateTime(LocalDateTime.now());
            // Known attribute, use attribute info
            if (AttributeKnowType.Known == knowType) {
                desiredAttribute
                        .setAttributeId(attribute.getId())
                        .setIdentifier(attribute.getIdentifier())
                        .setDataType(attribute.getDataType());
            } else if (AttributeKnowType.Unknown == knowType) {
                desiredAttribute
                        .setIdentifier(request.getIdentifier())
                        .setDataType(AttributeDataType.valueOf(request.getDataType()));
            }
            // @formatter:on
            this.repository.insert(desiredAttribute);
        }
        // update
        else {
            // Unknown attribute, allow to modify data type
            if (AttributeKnowType.Unknown == knowType) {
                desiredAttribute.setDataType(AttributeDataType.valueOf(request.getDataType()));
            }
            // @formatter:off
            desiredAttribute
                    .setDesiredValue(request.getDesiredValue())
                    .setVersion(desiredAttribute.getVersion() + Constants.ONE)
                    .setStatus(DesiredAttributeStatus.Pending)
                    .setLastUpdateTime(LocalDateTime.now());
            // @formatter:on
            this.repository.update(desiredAttribute);
        }
        this.deviceShadowService.updateDesired(desiredAttribute);
        return desiredAttribute;
    }

    @Override
    public DeviceAttributeDesired updateDesiredAttribute(String desiredAttributeId, UpdateDeviceDesiredAttributeRequest request) {
        DeviceAttributeDesired desiredAttribute = this.selectById(desiredAttributeId);
        if (desiredAttribute == null || desiredAttribute.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND, desiredAttributeId);
        }

        AttributeKnowType knowType = StringUtils.hasText(desiredAttribute.getAttributeId()) ? AttributeKnowType.Known : AttributeKnowType.Unknown;
        this.validate(knowType, request.getDesiredValue(), desiredAttribute.getAttributeId(), desiredAttribute.getIdentifier(), request.getDataType());

        // Unknown attribute，allow update data type
        if (AttributeKnowType.Unknown == knowType) {
            desiredAttribute.setDataType(AttributeDataType.valueOf(request.getDataType()));
        }

        // @formatter:off
        desiredAttribute
                .setDesiredValue(request.getDesiredValue())
                .setVersion(desiredAttribute.getVersion() + Constants.ONE)
                .setStatus(DesiredAttributeStatus.Pending)
                .setLastUpdateTime(LocalDateTime.now());
        // @formatter:on
        this.update(desiredAttribute);
        this.deviceShadowService.updateDesired(desiredAttribute);
        return desiredAttribute;
    }

    @Override
    public Attribute extractUnknownAttribute(String desiredAttributeId, ExtractUnknownDesiredAttributeRequest request, UserAuthorizedAddition authorizedAddition) {
        DeviceAttributeDesired desiredAttribute = this.selectById(desiredAttributeId);
        if (desiredAttribute == null || desiredAttribute.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND, desiredAttributeId);
        }
        if (!ObjectUtils.isEmpty(desiredAttribute.getAttributeId())) {
            throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_NOT_UNKNOWN, desiredAttribute.getIdentifier());
        }
        FunctionModule functionModule = this.functionModuleService.selectById(desiredAttribute.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, desiredAttribute.getModuleId());
        }
        // @formatter:off
        AddAttributeRequest addAttributeRequest = new AddAttributeRequest()
                .setProductId(functionModule.getProductId())
                .setModuleId(functionModule.getId())
                .setInfo(new AttributeInfoRequest()
                        .setName(request.getAttributeName())
                        .setIdentifier(desiredAttribute.getIdentifier())
                        .setDataType(desiredAttribute.getDataType().toString())
                        .setAddition(request.getAddition())
                        .setWritable(Boolean.TRUE));

        AttributeDTO attributeDTO = this.attributeService.addAttribute(addAttributeRequest,authorizedAddition);

        // After the unknown attribute is extracted as a known attribute,
        // set the attribute_id of the expected attribute to the known attribute id
        desiredAttribute.setAttributeId(attributeDTO.getId());

        this.repository.update(
                List.of(DEVICE_ATTRIBUTE_DESIRED.ATTRIBUTE_ID.set(attributeDTO.getId())),
                DEVICE_ATTRIBUTE_DESIRED.ID.eq(desiredAttribute.getId()));

        // @formatter:on
        return attributeDTO;
    }

    @Override
    public DeviceAttributeDesired deleteDesiredAttribute(String desiredAttributeId) {
        DeviceAttributeDesired desiredAttribute = this.selectById(desiredAttributeId);
        if (desiredAttribute == null || desiredAttribute.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND, desiredAttributeId);
        }
        desiredAttribute.setDeleted(Boolean.TRUE);
        this.deviceShadowService.removeDesired(desiredAttribute);
        return desiredAttribute;
    }

    /**
     * 验证关联数据是否有效
     *
     * @param knowType     属性知晓类型 {@link AttributeKnowType}
     * @param desiredValue 期望值
     * @param attributeId  已知属性ID
     * @param identifier   未知属性标识符
     * @param dataType     未知属性数据类型
     */
    private Attribute validate(AttributeKnowType knowType, String desiredValue,
                               String attributeId, String identifier, String dataType) {
        if (ObjectUtils.isEmpty(desiredValue)) {
            throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID);
        }

        AttributeDataType attributeDataType = !ObjectUtils.isEmpty(dataType) ? AttributeDataType.valueOf(dataType) : null;
        Attribute attribute = null;

        // AttributeKnowType#Known
        if (AttributeKnowType.Known == knowType) {
            if (ObjectUtils.isEmpty(attributeId)) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_ID_CANNOT_BLANK);
            }
            attribute = this.attributeService.selectById(attributeId);
            if (attribute == null || attribute.isDeleted()) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_FOUND, attributeId);
            }
            if (!attribute.isWritable()) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_WRITEABLE, attribute.getIdentifier());
            }
            if (attribute.getScope() == null || AttributeScope.Common != attribute.getScope()) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_COMMON_NOT_ALLOW_SET_DESIRED, attribute.getIdentifier());
            }
            // check enum value is define
            if (AttributeDataType.ENUM == attribute.getDataType()) {
                Map<String, String> enumMap = attribute.getAddition().getValueMap();
                if (!enumMap.containsKey(desiredValue)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID);
                }
            }
            attributeDataType = attribute.getDataType();
        }
        // AttributeKnowType#Unknown
        else if (AttributeKnowType.Unknown == knowType) {
            if (ObjectUtils.isEmpty(identifier)) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_IDENTIFIER_CANNOT_BLANK);
            }
            if (ObjectUtils.isEmpty(dataType)) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_DATA_TYPE_CANNOT_BLANK);
            }
        }

        // Validate data type
        try {
            switch (Objects.requireNonNull(attributeDataType)) {
                case INTEGER:
                    Integer.parseInt(desiredValue);
                    break;
                case DOUBLE:
                    Double.parseDouble(desiredValue);
                    break;
                case BOOLEAN:
                    if (!Boolean.TRUE.toString().equalsIgnoreCase(desiredValue) && !Boolean.FALSE.toString().equalsIgnoreCase(desiredValue)) {
                        throw new IllegalArgumentException("Invalid boolean value");
                    }
                    break;
                case DATE:
                    LocalDate.parse(desiredValue);
                    break;
                case DATETIME:
                    LocalDateTime.parse(desiredValue);
                    break;
                case TIME:
                    LocalTime.parse(desiredValue);
                    break;
                case TIMESTAMP:
                    Long.parseLong(desiredValue);
                    break;
                case JSON:
                    new ObjectMapper().readTree(desiredValue);
                    break;
                case ARRAY:
                    if (!(desiredValue.startsWith("[") && desiredValue.endsWith("]"))) {
                        throw new IllegalArgumentException("Invalid array format");
                    }
                    break;
            }
        } catch (Exception e) {
            throw new ApiException(StatusCodeConstants.DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID);
        }
        return attribute;
    }
}
