package cn.devicelinks.center.apis;

import cn.devicelinks.api.device.center.DeviceAttributeFeignClient;
import cn.devicelinks.api.device.center.model.request.QueryDeviceAttributeRequest;
import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.AttributeScope;
import cn.devicelinks.common.AttributeValueSource;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.*;
import cn.devicelinks.service.attribute.AttributeService;
import cn.devicelinks.service.device.DeviceAttributeDesiredService;
import cn.devicelinks.service.device.DeviceAttributeService;
import cn.devicelinks.service.device.DeviceService;
import cn.devicelinks.service.product.FunctionModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link DeviceAttributeFeignClient} 的服务端实现。
 *
 * <p>处理设备属性上报相关业务，包括属性的插入与基于版本的更新。
 * 本控制器通常由远程服务通过 OpenFeign 调用。
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device/attributes")
@RequiredArgsConstructor
@Slf4j
public class DeviceAttributeFeignController implements DeviceAttributeFeignClient {

    private final DeviceAttributeService deviceAttributeService;

    private final DeviceAttributeDesiredService deviceAttributeDesiredService;

    private final FunctionModuleService functionModuleService;

    private final DeviceService deviceService;

    private final AttributeService attributeService;

    @Override
    @PostMapping
    public ApiResponse<Boolean> saveOrUpdateAttributes(@RequestBody @Valid SaveOrUpdateDeviceAttributeRequest request) {
        Device device = deviceService.selectByDeviceId(request.getDeviceId());
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, request.getDeviceId());
        }
        // @formatter:off
        List<DeviceAttribute> deviceAttributeList = request.getAttributes()
                .stream()
                .map(attributeRequest -> {
                    FunctionModule functionModule = functionModuleService.selectByIdentifier(device.getProductId(), attributeRequest.getModule());
                    if (functionModule == null || functionModule.isDeleted()) {
                        throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, attributeRequest.getModule());
                    }
                    DeviceAttribute deviceAttribute = deviceAttributeService.selectByIdentifier(device.getId(), functionModule.getId(),
                            AttributeValueSource.DeviceReport,
                            attributeRequest.getIdentifier());
                    // Insert
                    if (deviceAttribute == null) {
                        deviceAttribute = new DeviceAttribute()
                                .setModuleId(functionModule.getId())
                                .setDeviceId(request.getDeviceId())
                                .setIdentifier(attributeRequest.getIdentifier())
                                .setValueSource(AttributeValueSource.DeviceReport)
                                .setValue(attributeRequest.getValue())
                                .setLastUpdateTime(LocalDateTime.now())
                                .setVersion(attributeRequest.getVersion());
                        // Associated Attributes
                        Attribute attribute = attributeService.selectAttributeByIdentifier(deviceAttribute.getIdentifier(), device.getProductId(),
                                deviceAttribute.getModuleId());
                        if (attribute != null && !attribute.isDeleted() && attribute.isEnabled()) {
                            if (attribute.getDataType().validate(deviceAttribute.getValue())) {
                                deviceAttribute.setAttributeId(attribute.getId());
                            } else {
                                throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_DATA_TYPE_NOT_MATCH, deviceAttribute.getIdentifier());
                            }
                        }
                    }
                    // Update
                    else {
                        // The requested version will only be updated if the existing version value is greater than the current version value.
                        if (deviceAttribute.getVersion() < attributeRequest.getVersion()) {
                            deviceAttribute.setValue(attributeRequest.getValue())
                                    .setLastUpdateTime(LocalDateTime.now())
                                    .setVersion(attributeRequest.getVersion());
                        } else {
                            log.error("设备：{}，上报的属性：[{}][{}]，值：{}，已存储的version[{}]值大于等于设备端上报的version[{}]值，跳过更新属性值.",
                                    request.getDeviceId(), attributeRequest.getModule(), attributeRequest.getIdentifier(),
                                    attributeRequest.getValue(), deviceAttribute.getVersion(), attributeRequest.getVersion());
                            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_VERSION_INVALID, attributeRequest.getIdentifier(), deviceAttribute.getVersion());
                        }
                    }
                    return deviceAttribute;
                }).toList();
        // @formatter:on
        deviceAttributeService.saveOrUpdateDeviceAttribute(deviceAttributeList);
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    @PostMapping("/query")
    public ApiResponse<List<DeviceAttribute>> getAttributes(@RequestBody @Valid QueryDeviceAttributeRequest request) {
        // @formatter:off
        return ApiResponse.success(deviceAttributeService.selectDeviceAttributes(
                request.getDeviceId(),
                AttributeValueSource.DeviceReport,
                request.getIdentifiers())
        );
        // @formatter:on
    }

    @Override
    @PostMapping("/subscribe/desired")
    public ApiResponse<List<DeviceAttributeDesired>> subscribeAttributesDesired(@RequestBody @Valid QueryDeviceAttributeRequest request) {
        // @formatter:off
        List<DeviceAttributeDesired> newlyAttributeList =
                deviceAttributeDesiredService.selectNewlyDesiredAttributes(
                        request.getDeviceId(),
                        AttributeScope.Common,
                        LocalDateTime.now());
        return ApiResponse.success(newlyAttributeList);
        // @formatter:on
    }
}
