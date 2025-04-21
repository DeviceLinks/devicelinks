package cn.devicelinks.api.support.feign;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.utils.HmacSignature;
import cn.devicelinks.framework.common.utils.HttpRequestUtils;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通过ApiKey发起请求时的签名工具类
 * 提供了一系列静态方法用于生成请求的签名，支持从HttpServletRequest和Feign的RequestTemplate中提取参数并生成签名。
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class FeignClientSignUtils {

    /**
     * 从Feign的RequestTemplate中获取查询字符串
     *
     * @param template Feign的RequestTemplate对象
     * @return 查询字符串，格式为"key1=value1&key2=value2"
     */
    public static String getQueryString(RequestTemplate template) {
        return template.queries().entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 按参数名正序排序
                .flatMap(e -> e.getValue().stream().map(v -> e.getKey() + "=" + v))
                .collect(Collectors.joining("&"));
    }

    /**
     * 从Feign的RequestTemplate中获取请求体字符串
     *
     * @param template Feign的RequestTemplate对象
     * @return 请求体字符串
     */
    public static String getBodyString(RequestTemplate template) {
        byte[] body = template.body();
        return body != null ? new String(body, StandardCharsets.UTF_8) : "";
    }

    /**
     * 对HttpServletRequest请求进行签名
     *
     * @param secret    密钥
     * @param timestamp 时间戳
     * @param request   HttpServletRequest对象
     * @return 生成的签名
     * @throws IOException 如果读取请求体时发生错误
     */
    public static String sign(String secret, String timestamp, HttpServletRequest request) throws IOException {
        String queryString = HttpRequestUtils.getQueryString(request);
        String bodyString = HttpRequestUtils.getBodyString(request);
        return sign(secret, timestamp, queryString, bodyString);
    }

    /**
     * 对Feign的RequestTemplate请求进行签名
     *
     * @param secret          密钥
     * @param timestamp       时间戳
     * @param requestTemplate Feign的RequestTemplate对象
     * @return 生成的签名
     */
    public static String sign(String secret, String timestamp, RequestTemplate requestTemplate) {
        String queryString = getQueryString(requestTemplate);
        String bodyString = getBodyString(requestTemplate);
        return sign(secret, timestamp, queryString, bodyString);
    }

    /**
     * 根据查询字符串、请求体字符串和时间戳生成签名
     *
     * @param secret      密钥
     * @param timestamp   时间戳
     * @param queryString 查询字符串
     * @param bodyString  请求体字符串
     * @return 生成的签名
     */
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

