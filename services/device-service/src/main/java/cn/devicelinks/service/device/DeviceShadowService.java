package cn.devicelinks.service.device;

import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.entity.DeviceShadow;
import cn.devicelinks.jdbc.BaseService;

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
     * @param attribute 需要更新的期望属性
     * @return 设备影子数据 {@link DeviceShadow}
     */
    DeviceShadow updateDesired(DeviceAttribute attribute);

    /**
     * 删除影子中期望属性
     *
     * @param attribute 期望属性 {@link DeviceAttribute}
     */
    void removeDesired(DeviceAttribute attribute);
}
