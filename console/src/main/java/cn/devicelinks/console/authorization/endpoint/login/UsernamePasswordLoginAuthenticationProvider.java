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

import cn.devicelinks.console.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.console.authorization.TokenRepository;
import cn.devicelinks.console.service.SysLogService;
import cn.devicelinks.console.service.SysUserSessionService;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.PlatformType;
import cn.devicelinks.framework.common.SessionStatus;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.common.pojos.SysLogAddition;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.common.pojos.SysUserSession;
import cn.devicelinks.framework.common.utils.HttpRequestUtils;
import cn.devicelinks.framework.common.utils.JacksonUtils;
import cn.devicelinks.framework.common.utils.ObjectIdUtils;
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
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Objects;

/**
 * The login authentication provider
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class UsernamePasswordLoginAuthenticationProvider implements AuthenticationProvider {
    private static final String PLATFORM_HEADER_NAME = "X-Platform-Type";
    private static final String TOKEN_ISSUER = "https://devicelinks.cn";
    private static final StatusCode USER_NOT_FOUND = StatusCode.build("USER_NOT_FOUND", "用户不存在.");
    private static final StatusCode PASSWORD_VERIFICATION_FAILED = StatusCode.build("PASSWORD_VERIFICATION_FAILED", "密码校验失败.");
    private static final String LOGIN_SUCCESS_MSG = "登录成功";
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final SysUserSessionService userSessionService;
    private final SysLogService logService;
    private final TokenRepository tokenRepository;
    private final JwtEncoder jwtEncoder;

    public UsernamePasswordLoginAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                                                       SysUserSessionService userSessionService, SysLogService logService,
                                                       TokenRepository tokenRepository, JwtEncoder jwtEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
        deviceLinksUserDetails.eraseCredentials();

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
