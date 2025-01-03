/*
 *   Copyright (C) 2024  恒宇少年
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

import cn.devicelinks.framework.common.security.access.resolver.BearerTokenResolver;
import cn.devicelinks.framework.common.security.access.resolver.DefaultBearerTokenResolver;
import cn.devicelinks.framework.common.context.Context;
import cn.devicelinks.framework.common.utils.HttpRequestUtils;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 请求上下文
 * <p>
 * 存储每次{@link HttpServletRequest}相关的数据，通过{@link RequestContextHolder}可以在任何地方获取
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestContext implements Context {
    private static final BearerTokenResolver tokenResolver = new DefaultBearerTokenResolver();
    private String requestId;
    private String uri;
    private String method;
    private String accessToken;
    private Map bodyParams;
    private Map urlParams;
    private Map<String, String> headers;
    private String ip;
    private boolean multipart;
    private HttpServletRequest request;

    public static RequestContext withRequest(HttpServletRequest request) {
        // @formatter:off
        String accessToken = tokenResolver.resolve(request);
        String requestId = UUIDUtils.generateNoDelimiter();
        return new RequestContext(requestId, request.getRequestURI(), request.getMethod(), accessToken,
                HttpRequestUtils.getRequestBodyParams(request),
                HttpRequestUtils.getPathParams(request),
                HttpRequestUtils.getRequestHeaders(request),
                HttpRequestUtils.getIp(request),
                HttpRequestUtils.isMultipart(request),
                request);
        // @formatter:on
    }
}
