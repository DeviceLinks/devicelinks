package cn.devicelinks.console.service;

import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.common.pojos.DeviceShadow;
import cn.devicelinks.framework.jdbc.BaseService;

/**
 * 设备影子业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceShadowService extends BaseService<DeviceShadow, String> {
    /**
     * 初始化设备影子数据
     *
     * @param deviceId 设备ID {@link DeviceShadow#getDeviceId()}
     * @return 初始设备影子 {@link DeviceShadow}
     */
    DeviceShadow initialShadow(String deviceId);

    /**
     * 查询指定设备的影子数据
     *
     * @param deviceId 设备ID {@link DeviceShadow#getDeviceId()}
     * @return 设备影子数据 {@link DeviceShadow}
     */
    DeviceShadow selectByDeviceId(String deviceId);

    /**
     * 更新期望属性影子数据
     *
     * @param attributeDesired 需要更新的期望属性
     * @return 设备影子数据 {@link DeviceShadow}
     */
    DeviceShadow updateDesired(DeviceAttributeDesired attributeDesired);

    /**
     * 删除影子中期望属性
     *
     * @param attributeDesired 期望属性 {@link DeviceAttributeDesired}
     */
    void removeDesired(DeviceAttributeDesired attributeDesired);
}
