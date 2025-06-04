package cn.devicelinks.api.device.center.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 生成动态令牌响应实体定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class GenerateDynamicTokenResponse {
    private String deviceId;
    private String plainTextDynamicToken;
    private LocalDateTime expirationTime;
}
