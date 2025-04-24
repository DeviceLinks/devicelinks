package cn.devicelinks.transport.support;

import cn.devicelinks.common.DeviceCredentialsType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 令牌验证响应
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class TokenValidationResponse {
    private DeviceCredentialsType credentialsType;
    private LocalDateTime expirationTime;
    private String deviceId;

    public TokenValidationResponse(DeviceCredentialsType credentialsType, LocalDateTime expirationTime, String deviceId) {
        this.credentialsType = credentialsType;
        this.expirationTime = expirationTime;
        this.deviceId = deviceId;
    }
}
