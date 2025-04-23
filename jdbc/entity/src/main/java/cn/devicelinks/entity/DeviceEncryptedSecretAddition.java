package cn.devicelinks.entity;

import cn.devicelinks.common.secret.AesProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备密钥加密附加信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceEncryptedSecretAddition {

    private AesProperties aes;
}
