package cn.devicelinks.center.authorization;

import lombok.Getter;

/**
 * 携带ApiKey认证请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ApiKeyAuthenticationRequest {
    private final String apiKey;
    private final String sign;
    private final Long timestamp;

    private ApiKeyAuthenticationRequest(String apiKey, String sign, Long timestamp) {
        this.apiKey = apiKey;
        this.sign = sign;
        this.timestamp = timestamp;
    }

    public static ApiKeyAuthenticationRequest build(String apiKey, String sign, Long timestamp) {
        return new ApiKeyAuthenticationRequest(apiKey, sign, timestamp);
    }
}
