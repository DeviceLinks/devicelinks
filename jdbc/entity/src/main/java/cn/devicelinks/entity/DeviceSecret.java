package cn.devicelinks.entity;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.common.DeviceSecretStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备密钥
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceSecret implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String deviceId;
    private String encryptedSecret;
    private DeviceEncryptedSecretAddition encryptedSecretAddition;
    private String secretVersion;
    private DeviceSecretStatus status;
    private LocalDateTime expiresTime;
    private LocalDateTime lastUseTime;
    private LocalDateTime createTime;
}
