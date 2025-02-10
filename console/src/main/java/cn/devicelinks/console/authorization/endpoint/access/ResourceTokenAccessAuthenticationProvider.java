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

import cn.devicelinks.console.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;
import cn.devicelinks.framework.jdbc.repositorys.SysUserSessionRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * The resource access authentication provider
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ResourceTokenAccessAuthenticationProvider implements AuthenticationProvider {

    private static final String TOKEN_EXPIRED_EXCEPTION_MSG = "Jwt expired";

    private final TokenRepository tokenRepository;

    private final SysUserSessionRepository userSessionRepository;

    private final JwtDecoder jwtDecoder;

    public ResourceTokenAccessAuthenticationProvider(TokenRepository tokenRepository, SysUserSessionRepository userSessionRepository, JwtDecoder jwtDecoder) {
        this.tokenRepository = tokenRepository;
        this.userSessionRepository = userSessionRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ResourceTokenAccessAuthenticationToken resourceTokenAccessAuthenticationToken = (ResourceTokenAccessAuthenticationToken) authentication;
        // check jwt
        try {
            Jwt jwt = jwtDecoder.decode(resourceTokenAccessAuthenticationToken.getToken());
            if (jwt.getExpiresAt() != null && Instant.now().isAfter(jwt.getExpiresAt())) {
                throw new DeviceLinksAuthorizationException(StatusCode.TOKEN_EXPIRED);
            }
        } catch (BadJwtException e) {
            if (e.getMessage().indexOf(TOKEN_EXPIRED_EXCEPTION_MSG) > Constants.ZERO) {
                throw new DeviceLinksAuthorizationException(StatusCode.TOKEN_EXPIRED);
            }
            throw new DeviceLinksAuthorizationException(StatusCode.TOKEN_JWT_PARSING_FAILED);
        }
        // Load DeviceLinksUserDetails from TokenRepository
        DeviceLinksUserDetails deviceLinksUserDetails = tokenRepository.get(resourceTokenAccessAuthenticationToken.getToken());
        if (deviceLinksUserDetails == null) {
            throw new DeviceLinksAuthorizationException(StatusCode.TOKEN_EXPIRED);
        }
        // Update session last active time
        this.updateSessionLastActiveTime(deviceLinksUserDetails.getSessionId());
        // Return authenticated ResourceTokenAccessAuthenticationToken
        return ResourceTokenAccessAuthenticationToken.authenticated(resourceTokenAccessAuthenticationToken.getToken(), deviceLinksUserDetails);
    }

    private void updateSessionLastActiveTime(String sessionId) {
        if (!ObjectUtils.isEmpty(sessionId)) {
            try {
                userSessionRepository.updateLastActiveTime(sessionId, LocalDateTime.now());
            } catch (Exception e) {
                throw new DeviceLinksAuthorizationException(StatusCode.UPDATE_LAST_ACTIVE_TIME_FAILED);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ResourceTokenAccessAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
