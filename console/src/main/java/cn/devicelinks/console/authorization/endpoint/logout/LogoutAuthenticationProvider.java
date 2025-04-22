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

package cn.devicelinks.console.authorization.endpoint.logout;

import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.entity.SysLog;
import cn.devicelinks.entity.SysLogAddition;
import cn.devicelinks.entity.SysUserSession;
import cn.devicelinks.component.web.utils.HttpRequestUtils;
import cn.devicelinks.common.utils.ObjectIdUtils;
import cn.devicelinks.service.system.SysLogService;
import cn.devicelinks.service.system.SysUserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.time.Instant;
import java.time.LocalDateTime;

import static cn.devicelinks.api.support.StatusCodeConstants.TOKEN_EXPIRED;
import static cn.devicelinks.api.support.StatusCodeConstants.TOKEN_JWT_PARSING_FAILED;

/**
 * The logout authentication provider
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LogoutAuthenticationProvider implements AuthenticationProvider {
    private static final String LOGOUT_SUCCESS_MSG = "登出成功";
    private final TokenRepository tokenRepository;
    private final SysUserSessionService userSessionService;
    private final SysLogService logService;
    private final JwtDecoder jwtDecoder;

    public LogoutAuthenticationProvider(TokenRepository tokenRepository, SysUserSessionService userSessionService, SysLogService logService, JwtDecoder jwtDecoder) {
        this.tokenRepository = tokenRepository;
        this.userSessionService = userSessionService;
        this.logService = logService;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LogoutAuthenticationToken logoutAuthenticationToken = (LogoutAuthenticationToken) authentication;
        // check jwt
        try {
            Jwt jwt = jwtDecoder.decode(logoutAuthenticationToken.getToken());
            if (jwt.getExpiresAt() != null && Instant.now().isAfter(jwt.getExpiresAt())) {
                throw new DeviceLinksAuthorizationException(TOKEN_EXPIRED);
            }
        } catch (BadJwtException e) {
            e.printStackTrace();
            throw new DeviceLinksAuthorizationException(TOKEN_JWT_PARSING_FAILED);
        }

        tokenRepository.remove(logoutAuthenticationToken.getToken());

        this.afterLogoutSuccess(logoutAuthenticationToken.getToken(), logoutAuthenticationToken.getRequest());

        return logoutAuthenticationToken;
    }

    private void afterLogoutSuccess(String token, HttpServletRequest request) {
        SysUserSession userSession = this.userSessionService.selectByToken(token);
        if (userSession != null) {
            this.userSessionService.updateLogoutTime(token);
            String ipAddress = HttpRequestUtils.getIp(request);
            // save user login log
            SysLog userLoginLog = new SysLog()
                    .setId(ObjectIdUtils.generateId())
                    .setUserId(userSession.getUserId())
                    .setSessionId(userSession.getId())
                    .setAction(LogAction.Logout)
                    .setObjectType(LogObjectType.User)
                    .setObjectId(userSession.getUserId())
                    .setSuccess(true)
                    .setMsg(LOGOUT_SUCCESS_MSG)
                    .setAddition(new SysLogAddition().setIpAddress(ipAddress))
                    .setCreateTime(LocalDateTime.now());
            this.logService.insert(userLoginLog);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LogoutAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
