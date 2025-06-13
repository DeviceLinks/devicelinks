package cn.devicelinks.service.device;

import cn.devicelinks.api.model.dto.AttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.*;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.*;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.entity.FunctionModule;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.DeviceAttributeRepository;
import cn.devicelinks.service.attribute.AttributeService;
import cn.devicelinks.service.product.FunctionModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.devicelinks.jdbc.tables.TDeviceAttribute.DEVICE_ATTRIBUTE;


/**
 * 设备属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeServiceImpl extends BaseServiceImpl<DeviceAttribute, String, DeviceAttributeRepository>
        implements DeviceAttributeService {

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private DeviceShadowService deviceShadowService;

    public DeviceAttributeServiceImpl(DeviceAttributeRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public List<DeviceAttributeLatestDTO> getLatestAttribute(String deviceId, String moduleId, String attributeName, String attributeIdentifier) {
        return this.repository.getLatestAttribute(deviceId, moduleId, attributeName, attributeIdentifier);
    }

    @Override
    public Attribute extractUnknownAttribute(String deviceAttributeId, ExtractUnknownDeviceAttributeRequest request, UserAuthorizedAddition authorizedAddition) {
        DeviceAttribute deviceAttribute = this.selectById(deviceAttributeId);
        if (deviceAttribute == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_FOUND, deviceAttributeId);
        }
        if (!ObjectUtils.isEmpty(deviceAttribute.getAttributeId())) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_UNKNOWN, deviceAttribute.getIdentifier());
        }
        if (AttributeScope.Device != deviceAttribute.getScope() && AttributeScope.Common != deviceAttribute.getScope()) {
            throw new ApiException(StatusCodeConstants.ATTRIBUTE_SCOPE_NOT_ALLOW_EXTRACT, deviceAttribute.getIdentifier(), deviceAttribute.getScope());
        }
        FunctionModule functionModule = this.functionModuleService.selectById(deviceAttribute.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, deviceAttribute.getModuleId());
        }
        // @formatter:off
        AddAttributeRequest addAttributeRequest = new AddAttributeRequest()
                .setProductId(functionModule.getProductId())
                .setModuleId(functionModule.getId())
                .setInfo(new AttributeInfoRequest()
                        .setName(request.getAttributeName())
                        .setIdentifier(deviceAttribute.getIdentifier())
                        .setDataType(request.getDataType())
                        .setAddition(request.getAddition())
                        .setWritable(request.isWritable()));

        AttributeDTO attributeDTO = this.attributeService.addAttribute(addAttributeRequest, authorizedAddition);

        // After the unknown attribute is extracted as a known attribute,
        // set the attribute_id of the expected attribute to the known attribute id
        deviceAttribute.setAttributeId(attributeDTO.getId());

        this.repository.update(
                List.of(DEVICE_ATTRIBUTE.ATTRIBUTE_ID.set(attributeDTO.getId())),
                DEVICE_ATTRIBUTE.ID.eq(deviceAttribute.getId()));

        // @formatter:on
        return attributeDTO;
    }

    @Override
    public DeviceAttribute checkAttributeIdChartField(String deviceId, String deviceAttributeId) {
        DeviceAttribute deviceAttribute = this.repository.selectOne(
                DEVICE_ATTRIBUTE.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE.ID.eq(deviceAttributeId));
        if (deviceAttribute == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_FOUND, deviceAttributeId);
        }
        // Is it a known attribute
        if (ObjectUtils.isEmpty(deviceAttribute.getAttributeId())) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_KNOWN, deviceAttribute.getIdentifier());
        }
        // Whether the attribute data type supports adding to the chart
        Attribute attribute = this.attributeService.selectById(deviceAttribute.getAttributeId());
        if (AttributeDataType.INTEGER != attribute.getDataType() && AttributeDataType.DOUBLE != attribute.getDataType()) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_DATA_TYPE_CANNOT_ADD_CHART, deviceAttribute.getIdentifier());
        }
        return deviceAttribute;
    }

    @Override
    public void saveOrUpdateDeviceAttribute(List<DeviceAttribute> deviceAttributeList) {
        for (DeviceAttribute deviceAttribute : deviceAttributeList) {
            // Insert
            if (ObjectUtils.isEmpty(deviceAttribute.getId())) {
                this.insert(deviceAttribute);
            }
            // Update
            else {
                this.update(deviceAttribute);
            }
        }
    }

    @Override
    public DeviceAttribute selectByIdentifier(String deviceId, String moduleId, String identifier) {
        // @formatter:off
        return this.repository.selectOne(DEVICE_ATTRIBUTE.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE.MODULE_ID.eq(moduleId),
                DEVICE_ATTRIBUTE.IDENTIFIER.eq(identifier));
        // @formatter:on
    }

    @Override
    public List<DeviceAttribute> selectDeviceAttributes(String deviceId, String[] identifiers) {
        return this.repository.selectDeviceAttributes(deviceId, identifiers);
    }

    @Override
    public List<DeviceAttribute> subscribeAttributesUpdate(String deviceId, LocalDateTime subscribeTime) {
        // @formatter:off
        return this.repository.select(
                DEVICE_ATTRIBUTE.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE.LAST_UPDATE_TIME.gte(subscribeTime)
        );
        // @formatter:on
    }

    @Override
    public DeviceAttribute addDeviceAttribute(Device device, AddDeviceAttributeRequest request) {
        // validate
        AttributeKnowType knowType = AttributeKnowType.valueOf(request.getKnowType());

        FunctionModule functionModule = this.functionModuleService.selectById(request.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, request.getModuleId());
        }
        if (!functionModule.getProductId().equals(device.getProductId())) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_BELONG_PRODUCT, functionModule.getIdentifier(), device.getProductId());
        }

        Attribute attribute = this.validate(knowType, request.getValue(),
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
        DeviceAttribute deviceAttribute = this.selectByIdentifier(device.getId(), request.getModuleId(), identifier);
        // insert
        if (deviceAttribute == null) {
            // @formatter:off
            deviceAttribute = new DeviceAttribute()
                    .setDeviceId(device.getId())
                    .setModuleId(request.getModuleId())
                    // Only common scope allow insert
                    .setScope(AttributeScope.Common)
                    .setVersion(Constants.ONE)
                    .setValue(request.getValue())
                    .setLastUpdateTime(LocalDateTime.now());
            // Known attribute, use attribute info
            if (AttributeKnowType.Known == knowType) {
                deviceAttribute
                        .setAttributeId(attribute.getId())
                        .setIdentifier(attribute.getIdentifier())
                        .setDataType(attribute.getDataType());
            } else if (AttributeKnowType.Unknown == knowType) {
                deviceAttribute
                        .setIdentifier(request.getIdentifier())
                        .setDataType(AttributeDataType.valueOf(request.getDataType()));
            }
            // @formatter:on
            this.repository.insert(deviceAttribute);
        }
        // update
        else {
            if (AttributeScope.Common != deviceAttribute.getScope()) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_SCOPE_NOT_ALLOW_ADD, deviceAttribute.getIdentifier(), deviceAttribute.getScope());
            }
            // Unknown attribute, allow to modify data type
            if (AttributeKnowType.Unknown == knowType) {
                deviceAttribute.setDataType(AttributeDataType.valueOf(request.getDataType()));
            }
            // @formatter:off
            deviceAttribute
                    .setValue(request.getValue())
                    .setVersion(deviceAttribute.getVersion() + Constants.ONE)
                    .setLastUpdateTime(LocalDateTime.now());
            // @formatter:on
            this.repository.update(deviceAttribute);
        }
        this.deviceShadowService.updateDesired(deviceAttribute);
        return deviceAttribute;
    }

    @Override
    public DeviceAttribute updateDeviceAttribute(String deviceAttributeId, UpdateDeviceAttributeRequest request) {
        DeviceAttribute deviceAttribute = this.selectById(deviceAttributeId);
        if (deviceAttribute == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_FOUND, deviceAttributeId);
        }
        if (AttributeScope.Server != deviceAttribute.getScope() && AttributeScope.Common != deviceAttribute.getScope()) {
            throw new ApiException(StatusCodeConstants.ATTRIBUTE_SCOPE_NOT_ALLOW_UPDATE, deviceAttribute.getIdentifier(), deviceAttribute.getScope());
        }
        AttributeKnowType knowType = StringUtils.hasText(deviceAttribute.getAttributeId()) ? AttributeKnowType.Known : AttributeKnowType.Unknown;
        this.validate(knowType, request.getValue(), deviceAttribute.getAttributeId(), deviceAttribute.getIdentifier(), request.getDataType());

        // Unknown attribute，allow update data type
        if (AttributeKnowType.Unknown == knowType) {
            deviceAttribute.setDataType(AttributeDataType.valueOf(request.getDataType()));
        } else {
            if (!deviceAttribute.getDataType().toString().equals(request.getDataType())) {
                throw new ApiException(StatusCodeConstants.KNOWN_ATTRIBUTES_NOT_UPDATE_DATA_TYPE);
            }
        }

        // @formatter:off
        deviceAttribute
                .setValue(request.getValue())
                .setVersion(deviceAttribute.getVersion() + Constants.ONE)
                .setLastUpdateTime(LocalDateTime.now());
        // @formatter:on
        this.update(deviceAttribute);
        this.deviceShadowService.updateDesired(deviceAttribute);
        return deviceAttribute;
    }

    /**
     * 验证关联数据是否有效
     *
     * @param knowType       属性知晓类型 {@link AttributeKnowType}
     * @param attributeValue 属性值
     * @param attributeId    已知属性ID
     * @param identifier     未知属性标识符
     * @param dataType       未知属性数据类型
     */
    private Attribute validate(AttributeKnowType knowType, String attributeValue,
                               String attributeId, String identifier, String dataType) {
        if (ObjectUtils.isEmpty(attributeValue)) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_VALUE_INVALID);
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
            // check enum value is define
            if (AttributeDataType.ENUM == attribute.getDataType()) {
                Map<String, String> enumMap = attribute.getAddition().getValueMap();
                if (!enumMap.containsKey(attributeValue)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_VALUE_INVALID);
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
        if (attributeDataType != null) {
            boolean validated = attributeDataType.validate(attributeValue);
            if (!validated) {
                throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_VALUE_INVALID);
            }
        }
        return attribute;
    }
}
