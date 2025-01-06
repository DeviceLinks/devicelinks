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
    wait,
    /**
     * 升级中
     */
    upgrading,
    /**
     * 已完成
     */
    completed,
    /**
     * 已取消
     */
    cancelled,
    /**
     * 未完成
     */
    unfinished
}
