package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.DeviceShadowStatus;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.entity.*;
import cn.devicelinks.jdbc.repository.DeviceShadowRepository;
import cn.devicelinks.service.attribute.AttributeService;
import cn.devicelinks.service.product.FunctionModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.devicelinks.jdbc.tables.TDeviceShadow.DEVICE_SHADOW;

/**
 * 设备影子业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceShadowServiceImpl extends BaseServiceImpl<DeviceShadow, String, DeviceShadowRepository> implements DeviceShadowService {

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private AttributeService attributeService;

    public DeviceShadowServiceImpl(DeviceShadowRepository repository) {
        super(repository);
    }

    @Override
    public DeviceShadow selectByDeviceId(String deviceId) {
        return this.repository.selectOne(DEVICE_SHADOW.DEVICE_ID.eq(deviceId));
    }

    @Override
    public DeviceShadow initialShadow(String deviceId) {
        // @formatter:off
        DeviceShadow deviceShadow = new DeviceShadow().setDeviceId(deviceId);
        // @formatter:on
        this.repository.insert(deviceShadow);
        return deviceShadow;
    }

    @Override
    public DeviceShadow updateDesired(DeviceAttribute attributeDesired) {
        FunctionModule functionModule = this.functionModuleService.selectById(attributeDesired.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, attributeDesired.getModuleId());
        }
        if (!ObjectUtils.isEmpty(attributeDesired.getAttributeId())) {
            Attribute attribute = this.attributeService.selectById(attributeDesired.getAttributeId());
            if (attribute == null || attribute.isDeleted()) {
                throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_FOUND, attributeDesired.getAttributeId());
            }
        }
        DeviceShadow deviceShadow = this.repository.selectOne(DEVICE_SHADOW.DEVICE_ID.eq(attributeDesired.getDeviceId()));
        if (deviceShadow == null) {
            deviceShadow = new DeviceShadow();
        }
        List<DeviceShadowDataAddition> shadowDataAdditionList = deviceShadow.getShadowData();
        Map<String, DeviceShadowDataAddition> shadowDataAdditionMap = !ObjectUtils.isEmpty(shadowDataAdditionList) ?
                shadowDataAdditionList.stream().collect(Collectors.toMap(DeviceShadowDataAddition::getModule, v -> v)) :
                new LinkedHashMap<>();
        // @formatter:off
        if (shadowDataAdditionMap.containsKey(functionModule.getIdentifier())) {
            DeviceShadowDataAddition addition = shadowDataAdditionMap.get(functionModule.getIdentifier());
            // Update desired attribute value
            addition.getDesired()
                    .getState()
                    .put(attributeDesired.getIdentifier(), attributeDesired.getValue());
            // Update metadata
            addition.getDesired()
                    .getMetadata()
                    .put(attributeDesired.getIdentifier(),
                            new DeviceShadowDataAddition.Metadata()
                                    .setTimestamp(System.currentTimeMillis())
                                    .setVersion(attributeDesired.getVersion())
                    );
        } else {
            DeviceShadowDataAddition addition = new DeviceShadowDataAddition(functionModule.getIdentifier());
            // Add desired attribute value
            addition.getDesired()
                    .getState()
                    .put(attributeDesired.getIdentifier(),
                            attributeDesired.getValue());
            // Update metadata
            addition.getDesired()
                    .getMetadata()
                    .put(attributeDesired.getIdentifier(), new DeviceShadowDataAddition.Metadata()
                            .setTimestamp(System.currentTimeMillis())
                            .setVersion(attributeDesired.getVersion()));
            shadowDataAdditionMap.put(functionModule.getIdentifier(), addition);
        }
        // @formatter:on
        deviceShadow.setShadowData(shadowDataAdditionMap.values().stream().toList())
                .setStatus(DeviceShadowStatus.WaitSync)
                .setLastUpdateTimestamp(LocalDateTime.now());
        this.repository.update(deviceShadow);
        return deviceShadow;
    }

    @Override
    public void removeDesired(DeviceAttribute attributeDesired) {
        FunctionModule functionModule = this.functionModuleService.selectById(attributeDesired.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, attributeDesired.getModuleId());
        }
        DeviceShadow deviceShadow = this.repository.selectOne(DEVICE_SHADOW.DEVICE_ID.eq(attributeDesired.getDeviceId()));
        if (deviceShadow != null) {
            List<DeviceShadowDataAddition> shadowDataAdditionList = deviceShadow.getShadowData();
            Map<String, DeviceShadowDataAddition> shadowDataAdditionMap = !ObjectUtils.isEmpty(shadowDataAdditionList) ?
                    shadowDataAdditionList.stream().collect(Collectors.toMap(DeviceShadowDataAddition::getModule, v -> v)) :
                    new LinkedHashMap<>();
            if (shadowDataAdditionMap.containsKey(functionModule.getIdentifier())) {
                DeviceShadowDataAddition addition = shadowDataAdditionMap.get(functionModule.getIdentifier());
                // @formatter:off
                // Remove desired attribute state
                addition.getDesired()
                        .getState()
                        .remove(attributeDesired.getIdentifier());
                // Remove desired attribute metadata
                addition.getDesired()
                        .getMetadata()
                        .remove(attributeDesired.getIdentifier());
                // @formatter:on
            }
        }
    }
}
