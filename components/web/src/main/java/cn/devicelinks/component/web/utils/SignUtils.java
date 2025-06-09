package cn.devicelinks.component.web.utils;

import cn.devicelinks.common.Constants;
import cn.devicelinks.common.utils.HmacSignature;
import cn.devicelinks.common.utils.HmacSignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 签名工具类，提供对 URL 查询参数、请求体内容等进行排序拼接，并基于 HMAC-SHA256 生成签名。
 * 适用于接口调用中的请求签名验证场景。
 */
public class SignUtils {

    public static final String PARAMETER_SIGN = "sign";

    /**
     * 将请求参数 map 转换为已按 key 正序排序的 query string。
     *
     * @param parameters 请求参数 map（可能包含多个值）
     * @return 排序拼接后的 query string 字符串（不包含 ?）
     */
    public static String getQueryString(Map<String, Collection<String>> parameters) {
        return parameters.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 按参数名正序排序
                .filter(e -> !e.getKey().equals(PARAMETER_SIGN))
                .flatMap(e -> e.getValue().stream().map(v -> e.getKey() + "=" + v))
                .collect(Collectors.joining("&"));
    }

    /**
     * 将请求体字节数组转换为 UTF-8 字符串。
     *
     * @param body 请求体字节数组
     * @return 字符串形式的 body 内容，若为空则返回空串
     */
    public static String getBodyString(byte[] body) {
        return body != null ? new String(body, StandardCharsets.UTF_8) : "";
    }

    /**
     * 对请求进行签名，基于 HttpServletRequest 获取 query string 和 body 内容。
     *
     * @param secret    签名密钥
     * @param timestamp 时间戳
     * @param request   原始请求对象
     * @return 生成的签名字符串
     * @throws IOException 请求体读取失败时抛出
     */
    public static String sign(HmacSignatureAlgorithm signatureAlgorithm, String secret, String timestamp, HttpServletRequest request) throws IOException {
        String queryString = HttpRequestUtils.getQueryString(request, List.of(PARAMETER_SIGN));
        String bodyString = HttpRequestUtils.getBodyString(request);
        return sign(signatureAlgorithm, secret, timestamp, queryString, bodyString);
    }

    /**
     * 基于 query 参数和 body 内容进行签名。
     *
     * @param secret          签名密钥
     * @param timestamp       时间戳
     * @param queryParameters 请求参数 map
     * @param bodyBytes       请求体字节数组
     * @return 生成的签名字符串
     */
    public static String sign(HmacSignatureAlgorithm signatureAlgorithm, String secret, String timestamp, Map<String, Collection<String>> queryParameters, byte[] bodyBytes) {
        String queryString = getQueryString(queryParameters);
        String bodyString = getBodyString(bodyBytes);
        return sign(signatureAlgorithm, secret, timestamp, queryString, bodyString);
    }

    /**
     * 基于 Spring 的 MultiValueMap 参数进行签名。
     *
     * @param secret    签名密钥
     * @param timestamp 时间戳
     * @param params    参数 map
     * @return 生成的签名字符串
     */
    public static String sign(HmacSignatureAlgorithm signatureAlgorithm, String secret, String timestamp, MultiValueMap<String, String> params) {
        String paramString = params.entrySet().stream().sorted(Map.Entry.comparingByKey()) // 按参数名正序排序
                .filter(e -> !e.getKey().equals(PARAMETER_SIGN))
                .flatMap(e -> e.getValue().stream().map(v -> e.getKey() + "=" + v))
                .collect(Collectors.joining("&"));
        return sign(signatureAlgorithm, secret, timestamp, paramString, null);
    }

    /**
     * 最底层签名方法，按 query + body + timestamp 拼接后进行 HMAC-SHA256 计算。
     *
     * @param secret      签名密钥
     * @param timestamp   时间戳
     * @param queryString 查询字符串（可为空）
     * @param bodyString  请求体字符串（可为空）
     * @return 生成的签名 hex 字符串
     */
    public static String sign(HmacSignatureAlgorithm signatureAlgorithm, String secret, String timestamp, String queryString, String bodyString) {
        // raw string
        // @formatter:off
        String raw = (!ObjectUtils.isEmpty(queryString) ? queryString : Constants.EMPTY_STRING) +
                (!ObjectUtils.isEmpty(bodyString) ? bodyString : Constants.EMPTY_STRING) +
                timestamp;
        // @formatter:on
        // generate hex sign
        return new HmacSignature(signatureAlgorithm, secret).toHex(raw);
    }
}
