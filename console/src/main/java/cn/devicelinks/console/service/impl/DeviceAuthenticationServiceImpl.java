package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceAuthenticationService;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAuthenticationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 设备鉴权业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceAuthenticationServiceImpl extends BaseServiceImpl<DeviceAuthentication, String, DeviceAuthenticationRepository> implements DeviceAuthenticationService {
    public DeviceAuthenticationServiceImpl(DeviceAuthenticationRepository repository) {
        super(repository);
    }

    @Override
    public DeviceAuthentication saveAuthentication(Device device, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition) {
        // @formatter:off
        DeviceAuthentication authentication = new DeviceAuthentication()
                .setId(UUIDUtils.generateNoDelimiter())
                .setDeviceId(device.getId())
                .setAuthenticationMethod(authenticationMethod)
                .setAddition(authenticationAddition)
                .setDeleted(Boolean.FALSE)
                .setCreateTime(LocalDateTime.now());
        // @formatter:on
        this.repository.insert(authentication);
        return authentication;
    }
}
