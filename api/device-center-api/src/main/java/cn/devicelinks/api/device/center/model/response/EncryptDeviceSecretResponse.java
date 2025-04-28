package cn.devicelinks.api.device.center.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 加密设备密钥响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class EncryptDeviceSecretResponse {
    private String encryptedSecret;
    private String iv;
    private String aesKeyVersion;
}
