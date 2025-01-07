package cn.devicelinks.framework.common;

import lombok.Getter;

/**
 * 网关协议
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum GatewayProtocol {
    JT_808("JT/T 808");
    private final String value;

    GatewayProtocol(String value) {
        this.value = value;
    }
}
