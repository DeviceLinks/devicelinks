package cn.devicelinks.component.authorization;

import cn.devicelinks.common.exception.DeviceLinksException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 认证安全配置类
 * <p>
 * 可以直接用于构建{@link SecurityFilterChain}
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class DeviceLinksAuthorizationWebSecurityConfigurer extends AbstractHttpConfigurer<DeviceLinksAuthorizationWebSecurityConfigurer, HttpSecurity> {

    private final Map<Class<? extends DeviceLinksAuthorizationEndpointConfigurer>, DeviceLinksAuthorizationEndpointConfigurer> configurers = new LinkedHashMap<>();

    /**
     * {@link RequestMatcher} with multiple {@link DeviceLinksAuthorizationEndpointConfigurer} connected via {@link OrRequestMatcher}
     */
    private RequestMatcher endpointsMatcher;

    protected abstract void initializeConfigurers();

    public DeviceLinksAuthorizationWebSecurityConfigurer() {
        this.initializeConfigurers();
    }

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        this.configurers.values().forEach(configurer -> {
            try {
                configurer.init(httpSecurity);
            } catch (Exception e) {
                throw new DeviceLinksException("Initializing security configurer class: " + configurer.getClass().getSimpleName() + ", encountered an exception.", e);
            }
            RequestMatcher requestMatcher = configurer.getRequestMatcher();
            if (requestMatcher != null) {
                requestMatchers.add(requestMatcher);
            }
        });
        this.endpointsMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        this.configurers.values().forEach(configurer -> {
            try {
                configurer.configure(httpSecurity);
            } catch (Exception e) {
                throw new DeviceLinksException("Configuration security configurer class: " + configurer.getClass().getSimpleName() + ", encountered an exception.", e);
            }
        });
    }

    public void configurers(Consumer<Map<Class<? extends DeviceLinksAuthorizationEndpointConfigurer>, DeviceLinksAuthorizationEndpointConfigurer>> consumer) {
        consumer.accept(this.configurers);
    }

    public void addConfigurer(Class<? extends DeviceLinksAuthorizationEndpointConfigurer> clazz, DeviceLinksAuthorizationEndpointConfigurer configurer) {
        this.configurers.put(clazz, configurer);
    }


    public RequestMatcher getEndpointsMatcher() {
        // Return a deferred RequestMatcher
        // since endpointsMatcher is constructed in init(HttpSecurity).
        return (request) -> this.endpointsMatcher.matches(request);
    }
}
