package cn.devicelinks.transport.http.authorization.endpoint.access;

import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import cn.devicelinks.service.device.DeviceCredentialsService;
import cn.devicelinks.service.device.DeviceService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 设备携带令牌认证配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceTokenAccessAuthenticationConfigurer extends DeviceLinksAuthorizationEndpointConfigurer {
    @Override
    public void init(HttpSecurity httpSecurity) {
        ApplicationContext applicationContext = httpSecurity.getSharedObject(ApplicationContext.class);
        DeviceService deviceService = applicationContext.getBean(DeviceService.class);
        DeviceCredentialsService deviceCredentialsService = applicationContext.getBean(DeviceCredentialsService.class);
        DeviceTokenAccessAuthenticationProvider deviceTokenAccessAuthenticationProvider =
                new DeviceTokenAccessAuthenticationProvider(deviceService, deviceCredentialsService);
        httpSecurity.authenticationProvider(deviceTokenAccessAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        DeviceTokenAccessFilter deviceTokenAccessFilter =
                new DeviceTokenAccessFilter(authenticationManager);
        httpSecurity.addFilterBefore(deviceTokenAccessFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
