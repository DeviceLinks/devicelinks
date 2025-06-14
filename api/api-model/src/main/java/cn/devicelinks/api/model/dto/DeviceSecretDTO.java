package cn.devicelinks.api.model.dto;

import cn.devicelinks.entity.DeviceSecret;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备密钥DTO
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceSecretDTO extends DeviceSecret {

    private String secret;
}
