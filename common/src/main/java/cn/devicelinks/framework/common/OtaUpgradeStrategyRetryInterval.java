package cn.devicelinks.framework.common;

/**
 * OTA升级策略重试间隔
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum OtaUpgradeStrategyRetryInterval {
    /**
     * 不重试
     */
    Not,
    /**
     * 立即重试
     */
    Immediately,
    /**
     * 10分钟后重试
     */
    Minute10,
    /**
     * 30分钟后重试
     */
    Minute30,
    /**
     * 1小时后重试
     */
    Hour1,
    /**
     * 24小时后重试
     */
    Hour24
}
