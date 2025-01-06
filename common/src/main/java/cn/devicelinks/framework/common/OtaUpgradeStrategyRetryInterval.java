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
    not,
    /**
     * 立即重试
     */
    immediately,
    /**
     * 10分钟后重试
     */
    minute_10,
    /**
     * 30分钟后重试
     */
    minute_30,
    /**
     * 1小时后重试
     */
    hour_1,
    /**
     * 24小时后重试
     */
    hour_24
}
