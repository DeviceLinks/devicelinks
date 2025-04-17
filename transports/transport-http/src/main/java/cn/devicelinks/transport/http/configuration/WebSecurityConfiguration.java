package cn.devicelinks.transport.http.configuration;

import cn.devicelinks.transport.http.authorization.endpoint.access.DeviceTokenAccessAuthenticationConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

/**
 * Web安全配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain deviceTokenAccessSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        return http.securityMatcher(AnyRequestMatcher.INSTANCE)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .with(new DeviceTokenAccessAuthenticationConfigurer(), Customizer.withDefaults())
                .build();
        // @formatter:on
    }
}
