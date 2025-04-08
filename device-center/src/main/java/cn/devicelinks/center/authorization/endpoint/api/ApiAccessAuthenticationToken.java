package cn.devicelinks.center.authorization.endpoint.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * Api访问认证令牌
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ApiAccessAuthenticationToken extends AbstractAuthenticationToken {
    private final String apiKey;
    private final String sign;
    private final Long timestamp;
    private final HttpServletRequest request;

    public ApiAccessAuthenticationToken(String apiKey, String sign, Long timestamp, HttpServletRequest request) {
        super(Collections.emptyList());
        this.apiKey = apiKey;
        this.sign = sign;
        this.timestamp = timestamp;
        this.request = request;
    }

    @Override
    public Object getCredentials() {
        return this.sign;
    }

    @Override
    public Object getPrincipal() {
        return this.apiKey;
    }
}
