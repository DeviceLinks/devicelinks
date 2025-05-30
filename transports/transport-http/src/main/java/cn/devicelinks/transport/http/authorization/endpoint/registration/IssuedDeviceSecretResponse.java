package cn.devicelinks.transport.http.authorization.endpoint.registration;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 颁发设备密钥响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class IssuedDeviceSecretResponse {
    private String deviceId;
    private String deviceName;
    private String deviceSecret;
}
