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

package cn.devicelinks.framework.common.utils;

import cn.devicelinks.framework.common.web.RepeatableRequestWrapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.minbox.framework.util.JsonUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * {@link HttpServletRequest} 工具类
 */
public class HttpRequestUtils {
    public static final String REQUESTED_WITH = "X-Requested-With";
    public static final String AJAX_REQUEST_HEADER = "XMLHttpRequest";
    public static final String USER_AGENT = "User-Agent";

    public static boolean isAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader(REQUESTED_WITH);
        return !ObjectUtils.isEmpty(requestedWith) && requestedWith.equalsIgnoreCase(AJAX_REQUEST_HEADER);
    }

    public static boolean isMultipart(ServletRequest request) {
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        } else {
            return contentType.toLowerCase().startsWith("multipart/");
        }
    }

    public static String getIp(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");

        String ip = getIpFromHeader(request, "X-Forwarded-For");
        if (isValidIp(ip)) {
            return ip;
        }

        ip = getIpFromHeader(request, "X-Real-IP");
        if (isValidIp(ip)) {
            return ip;
        }

        String[] headers = {"Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String header : headers) {
            ip = getIpFromHeader(request, header);
            if (isValidIp(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    private static String getIpFromHeader(HttpServletRequest request, String header) {
        String ip = request.getHeader(header);
        if (ip != null && ip.contains(",")) {
            return ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    private static boolean isValidIp(String ip) {
        return !StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
    }


    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        Map<String, String> headers = new HashMap();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    public static Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Assert.notNull(response, "response instance is null.");
        Map<String, String> headers = new HashMap();
        Iterator<String> iterator = response.getHeaderNames().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            String headerValue = response.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    public static String getHeader(HttpServletRequest request, String headerName) {
        Assert.notNull(request, "request instance is null.");
        Assert.notNull(headerName, "request header name is null.");
        return request.getHeader(headerName);
    }

    public static Map getRequestBodyParams(HttpServletRequest request) {
        String requestBody = !HttpRequestUtils.isMultipart(request) ? HttpRequestUtils.getBodyString(request) : null;
        if (!ObjectUtils.isEmpty(requestBody)) {
            return JsonUtils.fromJsonString(requestBody, Map.class);
        }
        return null;
    }

    public static Map getPathParams(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    public static String getUri(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return request.getRequestURI();
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT);
    }

    public static String getOS(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        if (ObjectUtils.isEmpty(userAgent)) {
            return "UnKnown";
        }
        try {
            Map<String, String> osList = new LinkedHashMap<>();
            osList.put("Windows", "Windows NT");
            osList.put("macOS", "Macintosh");
            osList.put("Linux", "Linux");
            osList.put("Android", "Android");
            osList.put("iOS", "iPhone|iPad");

            for (Map.Entry<String, String> entry : osList.entrySet()) {
                if (Pattern.compile(entry.getValue()).matcher(userAgent).find()) {
                    return entry.getKey();
                }
            }
            return "Unknown";
        } catch (Exception e) {
            return "UnKnown";
        }
    }

    public static String getBrowser(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        if (ObjectUtils.isEmpty(userAgent)) {
            return "UnKnown";
        }
        try {
            Map<String, String> browsers = new LinkedHashMap<>();
            browsers.put("Chrome", "Chrome");
            browsers.put("Firefox", "Firefox");
            browsers.put("Safari", "Safari");
            browsers.put("Edge", "Edg");
            browsers.put("Opera", "OPR");
            browsers.put("Internet Explorer", "MSIE|Trident");

            for (Map.Entry<String, String> entry : browsers.entrySet()) {
                if (Pattern.compile(entry.getValue()).matcher(userAgent).find()) {
                    return entry.getKey();
                }
            }
            return "Unknown";
        } catch (Exception e) {
            return "UnKnown";
        }
    }


    /**
     * 从HttpServletRequest中获取RequestParameter字符串
     *
     * @param request HttpServletRequest对象
     * @return 查询字符串，格式为"key1=value1&key2=value2"
     */
    public static String getQueryString(HttpServletRequest request) {
        return new TreeMap<>(request.getParameterMap()).entrySet().stream()
                .flatMap(e -> Arrays.stream(e.getValue()).map(v -> e.getKey() + "=" + v))
                .collect(Collectors.joining("&"));
    }


    /**
     * 从HttpServletRequest中获取RequestBody字符串
     *
     * @param request HttpServletRequest对象
     * @return 请求体字符串
     */
    public static String getBodyString(HttpServletRequest request) {
        try {
            // @formatter:off
            RepeatableRequestWrapper requestWrapper =
                    (request instanceof RepeatableRequestWrapper wrapper) ? wrapper :
                            new RepeatableRequestWrapper(request);
            // @formatter:on
            return requestWrapper.getBodyString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get RequestBody string.");
        }
    }
}
