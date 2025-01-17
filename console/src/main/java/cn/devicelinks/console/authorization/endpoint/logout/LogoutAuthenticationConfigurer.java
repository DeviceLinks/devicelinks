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

package cn.devicelinks.console.authorization.endpoint.logout;

import cn.devicelinks.console.authorization.HttpSecuritySharedObjectUtils;
import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.console.authorization.endpoint.AbstractAuthorizationEndpointConfigurer;
import cn.devicelinks.console.service.SysLogService;
import cn.devicelinks.console.service.SysUserSessionService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * The logout endpoint configurer
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LogoutAuthenticationConfigurer extends AbstractAuthorizationEndpointConfigurer {
    /**
     * The Logout Endpoint URI
     */
    private static final String LOGOUT_ENDPOINT_URI = "/auth/logout";

    private final RequestMatcher loginRequestMatcher;

    public LogoutAuthenticationConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
        this.loginRequestMatcher = new AntPathRequestMatcher(LOGOUT_ENDPOINT_URI, HttpMethod.POST.name());
    }

    @Override
    public void init(HttpSecurity httpSecurity) {
        ApplicationContext applicationContext = HttpSecuritySharedObjectUtils.getApplicationContext(httpSecurity);
        TokenRepository tokenRepository = applicationContext.getBean(TokenRepository.class);
        SysUserSessionService userSessionService = applicationContext.getBean(SysUserSessionService.class);
        SysLogService logService = applicationContext.getBean(SysLogService.class);
        JwtDecoder jwtDecoder = applicationContext.getBean(JwtDecoder.class);
        LogoutAuthenticationProvider logoutAuthenticationProvider = new LogoutAuthenticationProvider(tokenRepository, userSessionService, logService, jwtDecoder);
        httpSecurity.authenticationProvider(logoutAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        LogoutEndpointFilter logoutEndpointFilter = new LogoutEndpointFilter(authenticationManager, this.loginRequestMatcher);
        httpSecurity.addFilterBefore(logoutEndpointFilter, LogoutFilter.class);
    }

    @Override
    public RequestMatcher getRequestMatcher() {
        return this.loginRequestMatcher;
    }
}
