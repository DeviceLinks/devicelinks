package cn.devicelinks.framework.common;

/**
 * 设备状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum DeviceStatus {
    /**
     * 未激活，设备从未连接并上报数据
     */
    NotActivated,
    /**
     * 已激活，在线
     */
    Online,
    /**
     * 已激活，离线
     */
    Offline,
}
