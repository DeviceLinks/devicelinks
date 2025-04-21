package cn.devicelinks.transport.http.authorization.endpoint.credentials;

import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 设备动态令牌颁发认证配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceDynamicTokenIssuedAuthenticationConfigurer extends DeviceLinksAuthorizationEndpointConfigurer {
    /**
     * The Dynamic Token Endpoint URI
     */
    private static final String ISSUED_DYNAMIC_TOKEN_ENDPOINT_URI = "/authenticate/dynamic-token-credentials";

    @Getter
    private final RequestMatcher requestMatcher;

    public DeviceDynamicTokenIssuedAuthenticationConfigurer() {
        this.requestMatcher = new AntPathRequestMatcher(ISSUED_DYNAMIC_TOKEN_ENDPOINT_URI, HttpMethod.POST.name());
    }

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        ApplicationContext applicationContext = httpSecurity.getSharedObject(ApplicationContext.class);
        DeviceFeignClient deviceFeignClient = applicationContext.getBean(DeviceFeignClient.class);
        DeviceCredentialsFeignClient deviceCredentialsFeignClient = applicationContext.getBean(DeviceCredentialsFeignClient.class);
        DeviceDynamicTokenIssuedAuthenticationProvider dynamicTokenIssuedAuthenticationProvider =
                new DeviceDynamicTokenIssuedAuthenticationProvider(deviceFeignClient, deviceCredentialsFeignClient);
        httpSecurity.authenticationProvider(dynamicTokenIssuedAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        DeviceDynamicTokenIssuedFilter dynamicTokenIssuedFilter = new DeviceDynamicTokenIssuedFilter(authenticationManager, this.requestMatcher);
        httpSecurity.addFilterBefore(dynamicTokenIssuedFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
