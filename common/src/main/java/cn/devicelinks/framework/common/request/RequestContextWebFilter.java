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

package cn.devicelinks.framework.common.request;

import cn.devicelinks.framework.common.http.RequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * The {@link RequestContext} Web Filter
 *
 * @author 恒宇少年
 * @since 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "requestContextWebFilter")
@Slf4j
public class RequestContextWebFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
            RequestContext requestContext = RequestContext.withRequest(requestWrapper);
            RequestContextHolder.setContext(requestContext);
            chain.doFilter(requestWrapper, response);
        } finally {
            RequestContextHolder.removeContext();
        }
    }
}
