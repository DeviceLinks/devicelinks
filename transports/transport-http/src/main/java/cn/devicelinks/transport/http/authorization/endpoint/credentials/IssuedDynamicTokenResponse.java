package cn.devicelinks.transport.http.authorization.endpoint.credentials;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 颁发动态令牌颁发响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class IssuedDynamicTokenResponse {

    private String token;

    @JsonProperty("expires_at")
    private LocalDateTime expiresAt;
}
