package cn.devicelinks.service.ota;

import cn.devicelinks.entity.DeviceOta;
import cn.devicelinks.jdbc.BaseService;
import cn.devicelinks.api.model.dto.DeviceFunctionModuleOtaDTO;

import java.util.List;

/**
 * 设备OTA业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceOtaService extends BaseService<DeviceOta, String> {
    /**
     * 查询指定设备的Ota列表
     *
     * @param deviceId 设备ID {@link DeviceOta#getDeviceId()}
     * @return {@link DeviceOta}
     */
    List<DeviceFunctionModuleOtaDTO> selectByDeviceId(String deviceId);
}
