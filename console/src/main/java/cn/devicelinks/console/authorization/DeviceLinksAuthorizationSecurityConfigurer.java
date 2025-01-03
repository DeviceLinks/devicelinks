package cn.devicelinks.console.authorization;

import cn.devicelinks.console.authorization.endpoint.AbstractAuthorizationEndpointConfigurer;
import cn.devicelinks.console.authorization.endpoint.login.UsernamePasswordLoginAuthenticationConfigurer;
import cn.devicelinks.console.authorization.endpoint.logout.LogoutAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全认证统一配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceLinksAuthorizationSecurityConfigurer extends AbstractHttpConfigurer<DeviceLinksAuthorizationSecurityConfigurer, HttpSecurity> {
    private final Map<Class<? extends AbstractAuthorizationEndpointConfigurer>, AbstractAuthorizationEndpointConfigurer> configurers = createConfigurers();
    private RequestMatcher endpointsMatcher;

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        this.configurers.values().forEach(configurer -> {
            configurer.init(httpSecurity);
            RequestMatcher requestMatcher = configurer.getRequestMatcher();
            if (requestMatcher != null) {
                requestMatchers.add(requestMatcher);
            }
        });
        this.endpointsMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> configurer.configure(httpSecurity));
    }

    /**
     * 初始化支持的认证端点配置
     *
     * @return {@link AbstractAuthorizationEndpointConfigurer}集合
     */
    private Map<Class<? extends AbstractAuthorizationEndpointConfigurer>, AbstractAuthorizationEndpointConfigurer> createConfigurers() {
        Map<Class<? extends AbstractAuthorizationEndpointConfigurer>, AbstractAuthorizationEndpointConfigurer> configurers = new LinkedHashMap<>();
        // @formatter:off
        /*// Resource Token Access
        configurers.put(ResourceTokenAccessAuthenticationConfigurer.class,
                postProcess(new ResourceTokenAccessAuthenticationConfigurer(this::postProcess, this.getEndpointsMatcher())));*/
        // Login Endpoint
        configurers.put(UsernamePasswordLoginAuthenticationConfigurer.class,
                postProcess(new UsernamePasswordLoginAuthenticationConfigurer(this::postProcess)));
        // Logout Endpoint
        configurers.put(LogoutAuthenticationConfigurer.class,
                postProcess(new LogoutAuthenticationConfigurer(this::postProcess)));
        // @formatter:on
        return configurers;
    }

    public RequestMatcher getEndpointsMatcher() {
        // Return a deferred RequestMatcher
        // since endpointsMatcher is constructed in init(HttpSecurity).
        return (request) -> this.endpointsMatcher.matches(request);
    }
}
