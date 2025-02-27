package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceShadowService;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.pojos.DeviceShadow;
import cn.devicelinks.framework.common.pojos.DeviceShadowStateAddition;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceShadowRepository;
import org.springframework.stereotype.Service;

/**
 * 设备影子业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceShadowServiceImpl extends BaseServiceImpl<DeviceShadow, String, DeviceShadowRepository> implements DeviceShadowService {
    public DeviceShadowServiceImpl(DeviceShadowRepository repository) {
        super(repository);
    }

    @Override
    public DeviceShadow initialShadow(String deviceId) {
        // @formatter:off
        DeviceShadow deviceShadow = new DeviceShadow()
                .setDeviceId(deviceId)
                .setReportedState(new DeviceShadowStateAddition())
                .setReportedVersion(Constants.ONE)
                .setDesiredState(new DeviceShadowStateAddition())
                .setDesiredVersion(Constants.ONE);
        // @formatter:on
        this.repository.insert(deviceShadow);
        return deviceShadow;
    }
}
