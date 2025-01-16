package cn.devicelinks.framework.common;

/**
 * OTA升级进度状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum OtaUpgradeProgressState {
    /**
     * 等待推送
     */
    WaitPush,
    /**
     * 等待升级
     */
    Wait,
    /**
     * 升级成功
     */
    Success,
    /**
     * 升级失败
     */
    Failure,
    /**
     * 取消升级
     */
    Cancel
}
