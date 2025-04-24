package cn.devicelinks.api.device.center.response;

import cn.devicelinks.common.DeviceCredentialsType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 令牌解密后响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DecryptTokenResponse {
    private String token;
    private DeviceCredentialsType credentialsType;
    private LocalDateTime expirationTime;
    private String deviceId;
}
