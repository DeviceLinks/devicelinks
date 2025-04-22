package cn.devicelinks.center.authorization.endpoint.api;

import cn.devicelinks.center.configuration.DeviceCenterProperties;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Api访问认证配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ApiAccessAuthenticationConfigurer extends DeviceLinksAuthorizationEndpointConfigurer {
    private final List<DeviceCenterProperties.InternalServiceApiKey> apiKeys;

    public ApiAccessAuthenticationConfigurer(List<DeviceCenterProperties.InternalServiceApiKey> apiKeys) {
        this.apiKeys = apiKeys;
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        ApiAccessAuthenticationProvider apiAccessAuthenticationProvider = new ApiAccessAuthenticationProvider(this.apiKeys);
        httpSecurity.authenticationProvider(apiAccessAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        ApiAccessEndpointFilter apiAccessEndpointFilter =
                new ApiAccessEndpointFilter(authenticationManager);
        httpSecurity.addFilterBefore(apiAccessEndpointFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
