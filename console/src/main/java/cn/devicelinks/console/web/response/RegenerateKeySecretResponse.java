package cn.devicelinks.console.web.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 重新生成 KeySecret 响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class RegenerateKeySecretResponse {
    private String productId;
    private String productName;
    private String productKey;
    private String productSecret;
}
