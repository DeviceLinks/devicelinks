package cn.devicelinks.console.service;

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
}
