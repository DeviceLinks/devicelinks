package cn.devicelinks.center.configuration;

import cn.devicelinks.center.authorization.endpoint.api.ApiAccessAuthenticationConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import java.util.List;

/**
 * Web安全配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(DeviceCenterProperties.class)
@EnableWebSecurity
@Slf4j
public class WebSecurityConfiguration {
    private final DeviceCenterProperties deviceCenterProperties;

    public WebSecurityConfiguration(DeviceCenterProperties deviceCenterProperties) {
        this.deviceCenterProperties = deviceCenterProperties;
    }

    @Bean
    public SecurityFilterChain apiAccessSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        List<DeviceCenterProperties.InternalServiceApiKey> apiKeyList = deviceCenterProperties.getApiKeys();
        return http.securityMatcher(AnyRequestMatcher.INSTANCE)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .with(new ApiAccessAuthenticationConfigurer(apiKeyList), Customizer.withDefaults())
                .build();
        // @formatter:on
    }
}
