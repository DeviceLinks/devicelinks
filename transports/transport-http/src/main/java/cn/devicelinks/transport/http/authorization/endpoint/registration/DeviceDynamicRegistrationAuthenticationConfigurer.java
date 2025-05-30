package cn.devicelinks.transport.http.authorization.endpoint.registration;

import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.device.center.DeviceProfileFeignClient;
import cn.devicelinks.api.device.center.ProductFeignClient;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 设备动态注册认证端点配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceDynamicRegistrationAuthenticationConfigurer extends DeviceLinksAuthorizationEndpointConfigurer {
    /**
     * The Dynamic Registration Endpoint URI
     */
    private static final String DYNAMIC_REGISTRATION_ENDPOINT_URI = "/authenticate/dynamic-registration";

    @Getter
    private final RequestMatcher requestMatcher;

    public DeviceDynamicRegistrationAuthenticationConfigurer() {
        this.requestMatcher = PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, DYNAMIC_REGISTRATION_ENDPOINT_URI);
    }

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        ApplicationContext applicationContext = httpSecurity.getSharedObject(ApplicationContext.class);
        DeviceFeignClient deviceFeignClient = applicationContext.getBean(DeviceFeignClient.class);
        DeviceProfileFeignClient deviceProfileFeignClient = applicationContext.getBean(DeviceProfileFeignClient.class);
        ProductFeignClient productFeignClient = applicationContext.getBean(ProductFeignClient.class);
        DeviceDynamicRegistrationAuthenticationProvider dynamicRegistrationAuthenticationProvider =
                new DeviceDynamicRegistrationAuthenticationProvider(deviceFeignClient, deviceProfileFeignClient, productFeignClient);
        httpSecurity.authenticationProvider(dynamicRegistrationAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        DeviceDynamicRegistrationFilter dynamicRegistrationFilter = new DeviceDynamicRegistrationFilter(authenticationManager, this.requestMatcher);
        httpSecurity.addFilterBefore(dynamicRegistrationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
