/*
 *   Copyright (C) 2024-2025  DeviceLinks
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

package cn.devicelinks.console.authorization.endpoint.login;

import cn.devicelinks.console.authorization.HttpSecuritySharedObjectUtils;
import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import cn.devicelinks.service.system.SysLogService;
import cn.devicelinks.service.system.SysUserSessionService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * The login endpoint configurer
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class UsernamePasswordLoginAuthenticationConfigurer extends DeviceLinksAuthorizationEndpointConfigurer {
    /**
     * The Login Endpoint URI
     */
    private static final String LOGIN_ENDPOINT_URI = "/auth/login";

    private final RequestMatcher loginRequestMatcher;

    public UsernamePasswordLoginAuthenticationConfigurer() {
        this.loginRequestMatcher = new AntPathRequestMatcher(LOGIN_ENDPOINT_URI, HttpMethod.POST.name());
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        ApplicationContext applicationContext = HttpSecuritySharedObjectUtils.getApplicationContext(httpSecurity);
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        UserDetailsService userDetailsService = applicationContext.getBean(UserDetailsService.class);
        SysUserSessionService userSessionService = applicationContext.getBean(SysUserSessionService.class);
        SysLogService sysLogService = applicationContext.getBean(SysLogService.class);
        TokenRepository tokenRepository = applicationContext.getBean(TokenRepository.class);
        JwtEncoder jwtEncoder = applicationContext.getBean(JwtEncoder.class);
        UsernamePasswordLoginAuthenticationProvider loginAuthenticationProvider =
                new UsernamePasswordLoginAuthenticationProvider(passwordEncoder, userDetailsService, userSessionService,
                        sysLogService, tokenRepository, jwtEncoder);
        httpSecurity.authenticationProvider(loginAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        UsernamePasswordLoginEndpointFilter loginEndpointFilter = new UsernamePasswordLoginEndpointFilter(authenticationManager, this.loginRequestMatcher);
        httpSecurity.addFilterBefore(loginEndpointFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.loginRequestMatcher;
    }
}
