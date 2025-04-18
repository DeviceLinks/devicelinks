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

import cn.devicelinks.console.authorization.BearerTokenResolver;
import cn.devicelinks.console.authorization.DefaultBearerTokenResolver;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationExceptionFailureHandler;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The resource access authentication endpoint filter
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ResourceTokenAccessEndpointFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();


    private final AuthenticationFailureHandler authenticationFailureHandler = new DeviceLinksAuthorizationExceptionFailureHandler();

    public ResourceTokenAccessEndpointFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.bearerTokenResolver.resolve(request);
            if (ObjectUtils.isEmpty(token)) {
                throw new DeviceLinksAuthorizationException(StatusCode.INVALID_TOKEN);
            }

            // Create unauthenticated ResourceTokenAccessAuthenticationToken
            ResourceTokenAccessAuthenticationToken resourceTokenAccessAuthenticationToken = ResourceTokenAccessAuthenticationToken.unauthenticated(token);

            resourceTokenAccessAuthenticationToken =
                    (ResourceTokenAccessAuthenticationToken) this.authenticationManager.authenticate(resourceTokenAccessAuthenticationToken);

            resourceTokenAccessAuthenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(resourceTokenAccessAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (DeviceLinksAuthorizationException invalid) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, invalid);
        } catch (Exception e) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new DeviceLinksAuthorizationException(StatusCode.SYSTEM_EXCEPTION));
        }
    }
}
