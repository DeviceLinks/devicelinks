package cn.devicelinks.service.ota;

import cn.devicelinks.entity.DeviceOta;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.api.model.dto.DeviceFunctionModuleOtaDTO;
import cn.devicelinks.jdbc.repository.DeviceOtaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备OTA业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceOtaServiceImpl extends BaseServiceImpl<DeviceOta, String, DeviceOtaRepository> implements DeviceOtaService {
    public DeviceOtaServiceImpl(DeviceOtaRepository repository) {
        super(repository);
    }

    @Override
    public List<DeviceFunctionModuleOtaDTO> selectByDeviceId(String deviceId) {
        return this.repository.selectByDeviceId(deviceId);
    }
}
