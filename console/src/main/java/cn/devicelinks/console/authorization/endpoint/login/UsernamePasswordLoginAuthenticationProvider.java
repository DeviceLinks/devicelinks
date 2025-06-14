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

import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.common.PlatformType;
import cn.devicelinks.common.SessionStatus;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.console.authorization.DeviceLinksUserDetails;
import cn.devicelinks.entity.SysLog;
import cn.devicelinks.entity.SysLogAddition;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.entity.SysUserSession;
import cn.devicelinks.component.web.utils.HttpRequestUtils;
import cn.devicelinks.component.jackson.utils.JacksonUtils;
import cn.devicelinks.common.utils.ObjectIdUtils;
import cn.devicelinks.service.system.SysLogService;
import cn.devicelinks.service.system.SysUserService;
import cn.devicelinks.service.system.SysUserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Objects;

import static cn.devicelinks.api.support.StatusCodeConstants.PASSWORD_VERIFICATION_FAILED;
import static cn.devicelinks.api.support.StatusCodeConstants.USER_NOT_FOUND;

/**
 * The login authentication provider
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class UsernamePasswordLoginAuthenticationProvider implements AuthenticationProvider {
    private static final String PLATFORM_HEADER_NAME = "X-Platform-Type";
    private static final String TOKEN_ISSUER = "https://devicelinks.cn";
    private static final String LOGIN_SUCCESS_MSG = "登录成功";
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final SysUserSessionService userSessionService;
    private final SysLogService logService;
    private final SysUserService sysUserService;
    private final TokenRepository tokenRepository;
    private final JwtEncoder jwtEncoder;

    public UsernamePasswordLoginAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                                                       SysUserSessionService userSessionService, SysLogService logService,
                                                       SysUserService sysUserService,
                                                       TokenRepository tokenRepository, JwtEncoder jwtEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.sysUserService = sysUserService;
        this.userSessionService = userSessionService;
        this.logService = logService;
        this.tokenRepository = tokenRepository;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordLoginAuthenticationToken loginAuthenticationToken = (UsernamePasswordLoginAuthenticationToken) authentication;
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginAuthenticationToken.getUsername());
        if (userDetails == null) {
            throw new DeviceLinksAuthorizationException(USER_NOT_FOUND);
        }
        if (!this.passwordEncoder.matches(loginAuthenticationToken.getPassword(), userDetails.getPassword())) {
            throw new DeviceLinksAuthorizationException(PASSWORD_VERIFICATION_FAILED);
        }

        DeviceLinksUserDetails deviceLinksUserDetails = (DeviceLinksUserDetails) userDetails;
        // @formatter:off
        long tokenExpireIn = tokenRepository.getExpiresSeconds();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer(TOKEN_ISSUER)
                .subject(deviceLinksUserDetails.getUsername())
                .expiresAt(Instant.now().plusSeconds(tokenExpireIn))
                .issuedAt(Instant.now())
                .build();
        // @formatter:on
        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet));
        String token = jwt.getTokenValue();

        // erase credentials
        loginAuthenticationToken.eraseCredentials();

        String sessionId = ObjectIdUtils.generateId();
        deviceLinksUserDetails.setSessionId(sessionId);

        this.tokenRepository.save(token, deviceLinksUserDetails);

        this.afterLoginSuccess(sessionId, loginAuthenticationToken.getRequest(), jwt, deviceLinksUserDetails);

        return UsernamePasswordLoginAuthenticationToken.authenticated(loginAuthenticationToken.getUsername(),
                loginAuthenticationToken.getPassword(), deviceLinksUserDetails.getAuthorities(), token, tokenExpireIn);
    }

    private void afterLoginSuccess(String sessionId, HttpServletRequest request, Jwt jwt, DeviceLinksUserDetails deviceLinksUserDetails) {
        PlatformType platformType = this.getPlatformType(request);
        String ipAddress = HttpRequestUtils.getIp(request);
        SysUser user = deviceLinksUserDetails.getAuthorizedUserAddition().getUser();
        // @formatter:off
        // update user last login time
        sysUserService.updateLastLoginTime(user.getId(),LocalDateTime.now());
        // save user session
        SysUserSession userSession = new SysUserSession()
                .setId(sessionId)
                .setUserId(user.getId())
                .setUsername(deviceLinksUserDetails.getUsername())
                .setTokenValue(jwt.getTokenValue())
                .setPlatformType(platformType)
                .setStatus(SessionStatus.Normal)
                .setIssuedTime(Objects.requireNonNull(jwt.getIssuedAt()).atZone(ZoneId.systemDefault()).toLocalDateTime())
                .setExpiresTime(Objects.requireNonNull(jwt.getExpiresAt()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        this.userSessionService.insert(userSession);
        // save user login log
        SysLog userLoginLog = new SysLog()
                .setUserId(user.getId())
                .setSessionId(userSession.getId())
                .setAction(LogAction.Login)
                .setObjectType(LogObjectType.User)
                .setObjectId(user.getId())
                .setSuccess(Boolean.TRUE)
                .setMsg(LOGIN_SUCCESS_MSG)
                .setAddition(new SysLogAddition()
                        .setIpAddress(ipAddress)
                        .setOs(HttpRequestUtils.getOS(request))
                        .setBrowser(HttpRequestUtils.getBrowser(request)))
                .setActivityData(JacksonUtils.objectToJson(
                        new HashMap<>() {{
                            put("name", user.getName());
                            put("account", user.getAccount());
                            put("identity", user.getIdentity());
                        }}
                ));
        this.logService.insert(userLoginLog);
        // @formatter:on
    }

    private PlatformType getPlatformType(HttpServletRequest request) {
        String platform = request.getHeader(PLATFORM_HEADER_NAME);
        try {
            return ObjectUtils.isEmpty(platform) ? PlatformType.Pc : PlatformType.valueOf(platform);
        } catch (Exception e) {
            return PlatformType.Pc;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
