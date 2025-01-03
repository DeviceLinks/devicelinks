/*
 *   Copyright (C) 2024  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.console.configuration;

import cn.devicelinks.console.authorization.AuthorizedUserAdditionService;
import cn.devicelinks.console.authorization.DeviceLinksAuthorizationSecurityConfigurer;
import cn.devicelinks.console.authorization.DeviceLinksUserDetailsService;
import cn.devicelinks.console.authorization.JdbcAuthorizedUserAdditionService;
import cn.devicelinks.console.authorization.endpoint.access.ResourceTokenAccessAuthenticationConfigurer;
import cn.devicelinks.console.authorization.jose.LocalFileJwkSource;
import cn.devicelinks.console.startup.listener.InitializationJWKSourceKeyListener;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.HashSet;
import java.util.Set;


/**
 * Web安全配置类
 * <p>
 * 该类配置Web安全的默认链路，其中包含：
 * 开启全局验证: "/**"下的接口资源都需要认证通过后访问
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(ConsoleProperties.class)
@EnableWebSecurity
@Slf4j
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        DeviceLinksAuthorizationSecurityConfigurer authorizationSecurityConfigurer = new DeviceLinksAuthorizationSecurityConfigurer();
        RequestMatcher authorizationSecurityEndpointMatcher = authorizationSecurityConfigurer.getEndpointsMatcher();
        // @formatter:off
        http.securityMatcher(authorizationSecurityEndpointMatcher)
                .csrf((csrf) -> csrf.ignoringRequestMatchers(authorizationSecurityEndpointMatcher))
                // All request access requires authentication
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                // Disable Form Login
                .formLogin(AbstractHttpConfigurer::disable)
                // Enable AuthorizationSecurityConfigurer
                .with(authorizationSecurityConfigurer, Customizer.withDefaults());
        // @formatter:on
        return http.build();
    }

    @Bean
    public SecurityFilterChain resourceAccessSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher(AnyRequestMatcher.INSTANCE)
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .with(new ResourceTokenAccessAuthenticationConfigurer(),Customizer.withDefaults())
                .build();
    }

    /**
     * 配置用户数据加载方式
     * <p>
     * 从数据库中加载用户详细信息{@link UserDetails}
     *
     * @return {@link DeviceLinksUserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new DeviceLinksUserDetailsService();
    }

    /**
     * 授权用户的附加信息加载方式
     *
     * @return {@link JdbcAuthorizedUserAdditionService}
     */
    @Bean
    public AuthorizedUserAdditionService authorizedUserAdditionService() {
        return new JdbcAuthorizedUserAdditionService();
    }

    /**
     * 密码编码器
     * 选择使用{@link BCryptPasswordEncoder}作为密码的编码器，编码后的字符串无法解密只能通过{@link PasswordEncoder#matches}方法
     * 将登录时填写的密码原文与存储的加密字符串进行匹配验证
     *
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置JWK源
     * <p>
     * 认证中心首次启动时会初始化JWK密钥对到指定文件中，详见{@link InitializationJWKSourceKeyListener}
     *
     * @return {@link LocalFileJwkSource} 从本地文件中读取JWK源信息
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        return new LocalFileJwkSource();
    }

    /**
     * Jwt令牌解析器
     *
     * @param jwkSource {@link LocalFileJwkSource}
     * @return {@link JwtDecoder}
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        // @formatter:off
        Set<JWSAlgorithm> jwsAlgorithms = new HashSet<>();
        jwsAlgorithms.addAll(JWSAlgorithm.Family.RSA);
        jwsAlgorithms.addAll(JWSAlgorithm.Family.EC);
        jwsAlgorithms.addAll(JWSAlgorithm.Family.HMAC_SHA);
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> jwsKeySelector = new JWSVerificationKeySelector<>(jwsAlgorithms, jwkSource);
        jwtProcessor.setJWSKeySelector(jwsKeySelector);
        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {});
        // @formatter:on
        return new NimbusJwtDecoder(jwtProcessor);
    }

    /**
     * Jwt令牌编码器
     *
     * @param jwkSource {@link LocalFileJwkSource}
     * @return {@link JwtEncoder}
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
