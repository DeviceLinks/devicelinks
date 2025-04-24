package cn.devicelinks.transport.http.authorization.endpoint.access;

import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import cn.devicelinks.transport.support.TokenValidationService;
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
        DeviceFeignClient deviceFeignClient = applicationContext.getBean(DeviceFeignClient.class);
        TokenValidationService tokenValidationService = applicationContext.getBean(TokenValidationService.class);
        DeviceTokenAccessAuthenticationProvider deviceTokenAccessAuthenticationProvider =
                new DeviceTokenAccessAuthenticationProvider(deviceFeignClient, tokenValidationService);
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
