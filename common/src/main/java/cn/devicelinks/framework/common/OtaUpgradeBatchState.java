package cn.devicelinks.framework.common;

/**
 * OTA升级批次状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum OtaUpgradeBatchState {
    /**
     * 待升级
     */
    Wait,
    /**
     * 升级中
     */
    Upgrading,
    /**
     * 已完成
     */
    Completed,
    /**
     * 已取消
     */
    Cancelled,
    /**
     * 未完成
     */
    Unfinished
}
