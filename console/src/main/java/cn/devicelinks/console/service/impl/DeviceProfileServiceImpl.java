package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceProfileService;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 设备配置文件业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceProfileServiceImpl extends BaseServiceImpl<DeviceProfile, String, DeviceProfileRepository> implements DeviceProfileService {
    public DeviceProfileServiceImpl(DeviceProfileRepository repository) {
        super(repository);
    }
}
