package cn.devicelinks.framework.common;

/**
 * 设备指令状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum DeviceCommandStatus {
    /**
     * 等待发送
     */
    WAIT_SEND,
    /**
     * 发送失败
     */
    SEND_ERROR,
    /**
     * 等待响应
     */
    WAIT_RESPONSE,
    /**
     * 成功
     */
    SUCCESS
}
