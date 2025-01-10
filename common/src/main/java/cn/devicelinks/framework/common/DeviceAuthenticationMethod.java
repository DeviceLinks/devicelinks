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
     * 根据ProductSecret、DeviceNumber、DeviceSecret进行鉴权
     */
    DeviceCredential,
    /**
     * 固定签名
     * <p>
     * 根据固定的签名来进行鉴权，可以输入固定签名，也可以随机生成后作为固定签名使用
     */
    Signature,
    /**
     * 临时令牌
     * <p>
     * 使用受使用日期限制的令牌，可随机生成临时令牌并设置过期时间，也可以主动删除临时令牌
     */
    TemporaryToken
}
