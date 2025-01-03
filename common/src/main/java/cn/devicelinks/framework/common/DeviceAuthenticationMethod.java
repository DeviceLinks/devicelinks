package cn.devicelinks.framework.common;

/**
 * 设备鉴权方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum DeviceAuthenticationMethod {
    /**
     * 一机一密
     */
    DeviceCredential,
    /**
     * 固定签名
     */
    Signature,
    /**
     * 临时令牌
     */
    TemporaryToken
}
