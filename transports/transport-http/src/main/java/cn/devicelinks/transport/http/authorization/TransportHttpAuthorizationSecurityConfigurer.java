package cn.devicelinks.transport.http.authorization;

import cn.devicelinks.component.authorization.DeviceLinksAuthorizationWebSecurityConfigurer;
import cn.devicelinks.transport.http.authorization.endpoint.credentials.DeviceDynamicTokenIssuedAuthenticationConfigurer;
import cn.devicelinks.transport.http.authorization.endpoint.registration.DeviceDynamicRegistrationAuthenticationConfigurer;

/**
 * HTTP协议传输服务授权认证配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TransportHttpAuthorizationSecurityConfigurer extends DeviceLinksAuthorizationWebSecurityConfigurer {
    @Override
    protected void initializeConfigurers() {
        this.configurers(configurers -> {
            // Issued Dynamic Token Endpoint
            configurers.put(DeviceDynamicTokenIssuedAuthenticationConfigurer.class,
                    postProcess(new DeviceDynamicTokenIssuedAuthenticationConfigurer()));
            // Dynamic Registration Device Endpoint
            configurers.put(DeviceDynamicRegistrationAuthenticationConfigurer.class,
                    postProcess(new DeviceDynamicRegistrationAuthenticationConfigurer()));
        });
    }
}
