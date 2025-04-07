package cn.devicelinks.center.service.impl;

import cn.devicelinks.center.service.DeviceService;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.devicelinks.framework.jdbc.tables.TDevice.DEVICE;

/**
 * 设备业务逻辑接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceServiceImpl extends BaseServiceImpl<Device, String, DeviceRepository> implements DeviceService {
    public DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public Device getDeviceByName(String deviceName) {
        return repository.selectOne(DEVICE.DEVICE_NAME.eq(deviceName));
    }
}
