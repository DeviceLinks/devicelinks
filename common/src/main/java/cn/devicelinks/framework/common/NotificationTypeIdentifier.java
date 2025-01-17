package cn.devicelinks.framework.common;

/**
 * 通知类型标识符
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum NotificationTypeIdentifier {
    /**
     * 告警
     */
    Alarm,
    /**
     * 设备生命周期
     */
    DeviceLifecycle,
    /**
     * 实体动作
     */
    DataEntityAction,
    /**
     * 规则引擎故障
     */
    RuleEngineFailure
}
