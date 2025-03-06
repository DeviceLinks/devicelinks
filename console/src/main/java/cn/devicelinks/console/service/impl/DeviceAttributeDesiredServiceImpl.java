package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.*;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceDesiredAttributeRequest;
import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.DesiredAttributeStatus;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDesiredDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeDesiredRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

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
    private DeviceService deviceService;

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
    public PageResult<DeviceAttributeDesiredDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public DeviceAttributeDesired addDesiredAttribute(AddDeviceDesiredAttributeRequest request) {
        AttributeDataType dataType = AttributeDataType.valueOf(request.getDataType());
        // validate
        this.validate(request.getDeviceId(), request.getModuleId(), request.getAttributeId(), dataType, request.getDesiredValue());

        DeviceAttributeDesired desiredAttribute = this.selectByIdentifier(request.getDeviceId(), request.getModuleId(), request.getIdentifier());

        // insert
        if (desiredAttribute == null) {
            // @formatter:off
            desiredAttribute = new DeviceAttributeDesired()
                    .setDeviceId(request.getDeviceId())
                    .setModuleId(request.getModuleId())
                    .setAttributeId(request.getAttributeId())
                    .setIdentifier(request.getIdentifier())
                    .setDataType(dataType)
                    .setVersion(Constants.ONE)
                    .setDesiredValue(request.getDesiredValue())
                    .setLastUpdateTime(LocalDateTime.now());
            // @formatter:on
            this.repository.insert(desiredAttribute);
        }
        // update
        else {
            if (dataType != desiredAttribute.getDataType()) {
                throw new ApiException(StatusCodeConstants.DESIRED_DATA_TYPE_NOT_MATCH, desiredAttribute.getIdentifier());
            }
            if (!ObjectUtils.isEmpty(request.getAttributeId()) && ObjectUtils.isEmpty(desiredAttribute.getAttributeId())) {
                desiredAttribute.setAttributeId(request.getAttributeId());
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
    public DeviceAttributeDesired selectByIdentifier(String deviceId, String moduleId, String identifier) {
        // @formatter:off
        return this.repository.selectOne(
                DEVICE_ATTRIBUTE_DESIRED.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE_DESIRED.MODULE_ID.eq(moduleId),
                DEVICE_ATTRIBUTE_DESIRED.IDENTIFIER.eq(identifier),
                DEVICE_ATTRIBUTE_DESIRED.DELETED.eq(Boolean.FALSE));
        // @formatter:on
    }

    /**
     * 验证关联数据是否有效
     *
     * @param deviceId    设备ID {@link DeviceAttributeDesired#getDeviceId()}
     * @param moduleId    功能模块ID {@link DeviceAttributeDesired#getModuleId()}
     * @param attributeId 预定义属性ID {@link DeviceAttributeDesired#getAttributeId()}
     */
    private void validate(String deviceId, String moduleId, String attributeId, AttributeDataType dataType, String desiredValue) {
        if (ObjectUtils.isEmpty(desiredValue)) {
            throw new ApiException(StatusCodeConstants.INVALID_DESIRED_VALUE);
        }
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        FunctionModule functionModule = this.functionModuleService.selectById(moduleId);
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, moduleId);
        }
        if (!functionModule.getProductId().equals(device.getProductId())) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_BELONG_PRODUCT, functionModule.getIdentifier(), device.getProductId());
        }
        if (!ObjectUtils.isEmpty(attributeId)) {
            Attribute attribute = this.attributeService.selectById(attributeId);
            if (attribute == null || attribute.isDeleted()) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_FOUND, attributeId);
            }
            if (!attribute.getProductId().equals(functionModule.getProductId())) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_BELONG_PRODUCT, attribute.getIdentifier(), functionModule.getProductId());
            }
            if (!attribute.getModuleId().equals(functionModule.getId())) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_BELONG_FUNCTION_MODULE, attribute.getIdentifier(), functionModule.getIdentifier());
            }
            if (attribute.getDataType() != dataType) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_DATA_TYPE_NOT_MATCH, attribute.getIdentifier());
            }
            // check enum value is define
            if (AttributeDataType.ENUM == dataType) {
                Map<String, String> enumMap = attribute.getAddition().getValueMap();
                if (!enumMap.containsKey(desiredValue)) {
                    throw new ApiException(StatusCodeConstants.INVALID_DESIRED_VALUE);
                }
            }
        }
        try {
            switch (dataType) {
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
            throw new ApiException(StatusCodeConstants.INVALID_DESIRED_VALUE);
        }
    }
}
