package cn.devicelinks.api.device.center.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备动态注册响应
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DynamicRegistrationResponse {
    private String deviceId;
    private String deviceName;
    private String deviceSecret;
}
