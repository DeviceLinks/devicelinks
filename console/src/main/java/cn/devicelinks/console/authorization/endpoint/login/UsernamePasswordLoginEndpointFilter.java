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
import cn.devicelinks.framework.common.api.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The login endpoint filter
 * <p>
 * Provides "/auth/login" authentication endpoint, returns {@link LoginResponse} after successful login
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class UsernamePasswordLoginEndpointFilter extends OncePerRequestFilter {

    private static final String usernameParameter = "username";

    private static final String passwordParameter = "password";

    private static final StatusCode UNKNOWN_ERROR = StatusCode.build("UNKNOWN_ERROR", "遇到未知异常");

    private static final StatusCode USERNAME_CANNOT_EMPTY = StatusCode.build("USERNAME_CANNOT_EMPTY", "用户名不可以为空");

    private static final StatusCode PASSWORD_CANNOT_EMPTY = StatusCode.build("PASSWORD_CANNOT_EMPTY", "密码不可以为空");

    private final AuthenticationManager authenticationManager;

    private final RequestMatcher loginRequestMatcher;

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private final HttpMessageConverter<LoginResponse> loginHttpMessageConverter = new LoginHttpMessageConverter();

    private final AuthenticationConverter authenticationConverter = this::createAuthentication;

    private final AuthenticationSuccessHandler authenticationSuccessHandler = this::sendLoginResponse;

    private final AuthenticationFailureHandler authenticationFailureHandler = this::sendErrorResponse;

    public UsernamePasswordLoginEndpointFilter(AuthenticationManager authenticationManager, RequestMatcher loginRequestMatcher) {
        this.authenticationManager = authenticationManager;
        this.loginRequestMatcher = loginRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.loginRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordLoginAuthenticationToken authRequest = (UsernamePasswordLoginAuthenticationToken) authenticationConverter.convert(request);
            authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
            UsernamePasswordLoginAuthenticationToken usernamePasswordLoginAuthenticationToken =
                    (UsernamePasswordLoginAuthenticationToken) this.authenticationManager.authenticate(authRequest);
            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, usernamePasswordLoginAuthenticationToken);
        } catch (AuthenticationException e) {
            DeviceLinksAuthorizationException deviceLinksAuthorizationException = new DeviceLinksAuthorizationException(e.getMessage());
            if (e instanceof DeviceLinksAuthorizationException) {
                deviceLinksAuthorizationException = (DeviceLinksAuthorizationException) e;
            }
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, deviceLinksAuthorizationException);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new DeviceLinksAuthorizationException(UNKNOWN_ERROR));
        }
    }

    private String getUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    private String getPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    private Authentication createAuthentication(HttpServletRequest request) throws AuthenticationException {
        String username = getUsername(request);
        username = (username != null) ? username.trim() : "";
        if (ObjectUtils.isEmpty(username)) {
            throw new DeviceLinksAuthorizationException(USERNAME_CANNOT_EMPTY);
        }
        String password = getPassword(request);
        password = (password != null) ? password : "";
        if (ObjectUtils.isEmpty(password)) {
            throw new DeviceLinksAuthorizationException(PASSWORD_CANNOT_EMPTY);
        }
        return UsernamePasswordLoginAuthenticationToken.unauthenticated(request, username, password);
    }

    private void sendLoginResponse(HttpServletRequest request, HttpServletResponse response,
                                   Authentication authentication) throws IOException {
        UsernamePasswordLoginAuthenticationToken authenticationToken = (UsernamePasswordLoginAuthenticationToken) authentication;
        LoginResponse successResponse = LoginResponse.success(authenticationToken.getToken(), authenticationToken.getExpiresIn());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.loginHttpMessageConverter.write(successResponse, null, httpResponse);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
                                   AuthenticationException authenticationException) throws IOException {
        DeviceLinksAuthorizationException deviceLinksAuthorizationException = (DeviceLinksAuthorizationException) authenticationException;
        LoginResponse errorResponse =
                LoginResponse.error(deviceLinksAuthorizationException.getStatusCode() != null ?
                        deviceLinksAuthorizationException.getStatusCode().getMessage() : deviceLinksAuthorizationException.getMessage());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.loginHttpMessageConverter.write(errorResponse, null, httpResponse);
    }
}
