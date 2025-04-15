package cn.devicelinks.service.device;

import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceSecretRepository;
import org.springframework.stereotype.Service;

/**
 * 设备密钥业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceSecretServiceImpl extends BaseServiceImpl<DeviceSecret, String, DeviceSecretRepository> implements DeviceSecretService {
    public DeviceSecretServiceImpl(DeviceSecretRepository repository) {
        super(repository);
    }
}
