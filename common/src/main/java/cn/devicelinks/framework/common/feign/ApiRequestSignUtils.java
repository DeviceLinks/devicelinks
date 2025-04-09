package cn.devicelinks.framework.common.feign;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.utils.HmacSignature;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 通过ApiKey发起请求时的签名工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ApiRequestSignUtils {

    public static String getQueryString(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .flatMap(e -> Arrays.stream(e.getValue()).map(v -> e.getKey() + "=" + v))
                .collect(Collectors.joining("&"));
    }

    public static String getQueryString(RequestTemplate template) {
        return template.queries().entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(v -> e.getKey() + "=" + v))
                .collect(Collectors.joining("&"));
    }

    public static String getBodyString(HttpServletRequest request) throws IOException {
        try (InputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining());
        }
    }

    public static String getBodyString(RequestTemplate template) {
        byte[] body = template.body();
        return body != null ? new String(body, StandardCharsets.UTF_8) : "";
    }

    public static String sign(String secret, String timestamp, HttpServletRequest request) throws IOException {
        String queryString = getQueryString(request);
        String bodyString = getBodyString(request);
        return sign(secret, timestamp, queryString, bodyString);
    }

    public static String sign(String secret, String timestamp, RequestTemplate requestTemplate) {
        String queryString = getQueryString(requestTemplate);
        String bodyString = getBodyString(requestTemplate);
        return sign(secret, timestamp, queryString, bodyString);
    }

    public static String sign(String secret, String timestamp, String queryString, String bodyString) {
        // raw string
        // @formatter:off
        String raw = (!ObjectUtils.isEmpty(queryString) ? queryString : Constants.EMPTY_STRING) +
                (!ObjectUtils.isEmpty(bodyString) ? bodyString : Constants.EMPTY_STRING) +
                timestamp;
        // @formatter:on
        // generate hex sign
        return HmacSignature.hmacSha256(secret).toHex(raw);
    }
}
