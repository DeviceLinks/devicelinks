package cn.devicelinks.framework.common;

/**
 * 设备鉴权方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum DeviceAuthenticationMethod {
    /**
     * 一型一密
     * <p>
     * 根据ProductKey以及ProductSecret进行鉴权
     */
    ProductCredential,
    /**
     * 一机一密
     * <p>
     * 根据ProductSecret、DeviceCode、DeviceSecret进行鉴权
     */
    DeviceCredential,
    /**
     * 请求令牌
     */
    AccessToken,
    /**
     * MQTT基础认证
     * <p>
     * 客户端ID、用户名、密码
     */
    MqttBasic,
    /**
     * X.509证书
     * <p>
     * PEM格式证书
     */
    X509
}
