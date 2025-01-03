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

import cn.devicelinks.console.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.console.service.SysUserSessionService;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.pojos.SysUserSession;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.time.Instant;

/**
 * The logout authentication provider
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LogoutAuthenticationProvider implements AuthenticationProvider {

    private final TokenRepository tokenRepository;
    private final SysUserSessionService userSessionService;
    private final JwtDecoder jwtDecoder;

    public LogoutAuthenticationProvider(TokenRepository tokenRepository, SysUserSessionService userSessionService, JwtDecoder jwtDecoder) {
        this.tokenRepository = tokenRepository;
        this.userSessionService = userSessionService;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LogoutAuthenticationToken logoutAuthenticationToken = (LogoutAuthenticationToken) authentication;
        // check jwt
        try {
            Jwt jwt = jwtDecoder.decode(logoutAuthenticationToken.getToken());
            if (jwt.getExpiresAt() != null && Instant.now().isAfter(jwt.getExpiresAt())) {
                throw new DeviceLinksAuthorizationException(StatusCode.TOKEN_EXPIRED);
            }
        } catch (BadJwtException e) {
            throw new DeviceLinksAuthorizationException(StatusCode.TOKEN_JWT_PARSING_FAILED);
        }

        tokenRepository.remove(logoutAuthenticationToken.getToken());

        this.afterLogoutSuccess(logoutAuthenticationToken.getToken());

        return logoutAuthenticationToken;
    }

    private void afterLogoutSuccess(String token) {
        SysUserSession userSession = this.userSessionService.selectByToken(token);
        if (userSession != null) {
            this.userSessionService.updateLogoutTime(token);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LogoutAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
