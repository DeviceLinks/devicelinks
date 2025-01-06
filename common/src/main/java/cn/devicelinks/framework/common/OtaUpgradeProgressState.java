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
    WAIT_PUSH,
    /**
     * 等待升级
     */
    WAIT,
    /**
     * 升级成功
     */
    SUCCESS,
    /**
     * 升级失败
     */
    FAILURE,
    /**
     * 取消升级
     */
    CANCEL
}
