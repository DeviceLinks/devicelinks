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

package cn.devicelinks.console.authorization.endpoint.access;

import cn.devicelinks.console.authorization.HttpSecuritySharedObjectUtils;
import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationEndpointConfigurer;
import cn.devicelinks.framework.jdbc.repositorys.SysUserSessionRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The resource access authentication configurer
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ResourceTokenAccessAuthenticationConfigurer extends DeviceLinksAuthorizationEndpointConfigurer {
    @Override
    public void init(HttpSecurity httpSecurity) {
        ApplicationContext applicationContext = HttpSecuritySharedObjectUtils.getApplicationContext(httpSecurity);
        TokenRepository tokenRepository = applicationContext.getBean(TokenRepository.class);
        JwtDecoder jwtDecoder = applicationContext.getBean(JwtDecoder.class);
        SysUserSessionRepository userSessionRepository = applicationContext.getBean(SysUserSessionRepository.class);
        ResourceTokenAccessAuthenticationProvider resourceTokenAccessAuthenticationProvider =
                new ResourceTokenAccessAuthenticationProvider(tokenRepository, userSessionRepository, jwtDecoder);
        httpSecurity.authenticationProvider(resourceTokenAccessAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = HttpSecuritySharedObjectUtils.getAuthenticationManager(httpSecurity);
        ResourceTokenAccessEndpointFilter resourceTokenAccessEndpointFilter =
                new ResourceTokenAccessEndpointFilter(authenticationManager);
        httpSecurity.addFilterBefore(resourceTokenAccessEndpointFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
