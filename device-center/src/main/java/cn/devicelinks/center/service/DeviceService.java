package cn.devicelinks.center.service;

import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.jdbc.BaseService;

/**
 * 设备业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceService extends BaseService<Device, String> {
    Device getDeviceByName(String deviceName);
}
