package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备鉴权方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum DeviceAuthenticationMethod {
    ProductCredential("一型一密"),
    DeviceCredential("一机一密"),
    MqttBasic("MQTT Basic"),
    X509("X.509证书");
    private final String description;

    DeviceAuthenticationMethod(String description) {
        this.description = description;
    }
}
