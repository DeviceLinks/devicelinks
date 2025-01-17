package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备鉴权附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DeviceAuthenticationAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String accessToken;

    private String x509Pem;

    private MqttBasic mqttBasic;

    @Data
    @Accessors(chain = true)
    public static class MqttBasic {
        private String clientId;
        private String username;
        private String password;
    }
}
