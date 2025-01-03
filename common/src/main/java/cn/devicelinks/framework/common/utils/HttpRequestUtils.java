package cn.devicelinks.framework.common.utils;

import cn.devicelinks.framework.common.http.RequestWrapper;
import cn.devicelinks.framework.common.http.ResponseWrapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.minbox.framework.util.JsonUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link HttpServletRequest} 工具类
 */
public class HttpRequestUtils {
    public static final String REQUESTED_WITH = "X-Requested-With";
    public static final String AJAX_REQUEST_HEADER = "XMLHttpRequest";

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
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
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

    public static String getRequestBody(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        RequestWrapper requestWrapper;
        if (request instanceof RequestWrapper) {
            requestWrapper = (RequestWrapper) request;
        } else {
            requestWrapper = new RequestWrapper(request);
        }
        return requestWrapper.getBody();
    }

    public static Map getRequestBodyParams(HttpServletRequest request) {
        String requestBody = !HttpRequestUtils.isMultipart(request) ? HttpRequestUtils.getRequestBody(request) : null;
        if (!ObjectUtils.isEmpty(requestBody)) {
            return JsonUtils.fromJsonString(requestBody, Map.class);
        }
        return null;
    }

    public static String getResponseBody(HttpServletResponse response) throws IOException {
        if (response instanceof ResponseWrapper) {
            ResponseWrapper responseWrapper = (ResponseWrapper) response;
            byte[] copy = responseWrapper.getCopy();
            return new String(copy, ResponseWrapper.DEFAULT_CHARACTER_ENCODING);
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
}
