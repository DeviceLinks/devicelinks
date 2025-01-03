package cn.devicelinks.framework.common;

/**
 * 设备指令状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum CommandStatus {
    /**
     * 待发送
     */
    wait,
    /**
     * 已发送
     */
    sent,
    /**
     * 发送成功
     */
    success,
    /**
     * 发送失败
     */
    failure
}
