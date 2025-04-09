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

import cn.devicelinks.console.authorization.BearerTokenResolver;
import cn.devicelinks.console.authorization.DefaultBearerTokenResolver;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.console.authorization.endpoint.access.ResourceAccessHttpMessageConverter;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.api.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The logout endpoint filter
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LogoutEndpointFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final RequestMatcher logoutRequestMatcher;

    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    private final HttpMessageConverter<ApiResponse> httpMessageConverter = new ResourceAccessHttpMessageConverter();

    private final AuthenticationConverter authenticationConverter = this::createAuthentication;

    private final AuthenticationSuccessHandler authenticationSuccessHandler = this::sendSuccessResponse;

    private final AuthenticationFailureHandler authenticationFailureHandler = this::sendErrorResponse;

    public LogoutEndpointFilter(AuthenticationManager authenticationManager, RequestMatcher loginRequestMatcher) {
        this.authenticationManager = authenticationManager;
        this.logoutRequestMatcher = loginRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.logoutRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            LogoutAuthenticationToken logoutAuthenticationToken = (LogoutAuthenticationToken) this.authenticationConverter.convert(request);
            this.authenticationManager.authenticate(logoutAuthenticationToken);
            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, logoutAuthenticationToken);
        } catch (Exception e) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    e instanceof DeviceLinksAuthorizationException ? (AuthenticationException) e : new DeviceLinksAuthorizationException(StatusCode.SYSTEM_EXCEPTION));
        }
    }

    private Authentication createAuthentication(HttpServletRequest request) throws OAuth2AuthenticationException {
        String token = this.bearerTokenResolver.resolve(request);
        if (ObjectUtils.isEmpty(token)) {
            throw new DeviceLinksAuthorizationException(StatusCode.INVALID_TOKEN);
        }
        return new LogoutAuthenticationToken(token, request);
    }

    private void sendSuccessResponse(HttpServletRequest request, HttpServletResponse response,
                                     Authentication authentication) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.httpMessageConverter.write(ApiResponse.success(null), null, httpResponse);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
                                   AuthenticationException authenticationException) throws IOException {
        DeviceLinksAuthorizationException deviceLinksAuthorizationException = (DeviceLinksAuthorizationException) authenticationException;
        ApiResponse errorResponse = ApiResponse.error(deviceLinksAuthorizationException.getStatusCode());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.httpMessageConverter.write(errorResponse, null, httpResponse);
    }
}
