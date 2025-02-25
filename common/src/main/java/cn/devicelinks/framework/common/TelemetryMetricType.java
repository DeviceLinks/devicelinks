package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 遥测指标类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum TelemetryMetricType {
    DeviceMetadata("设备元数据"),
    DeviceState("设备状态"),
    ProtocolMetadata("协议元数据"),
    TriggerEvent("触发事件"),
    BusinessData("业务数据"),
    Log("日志");

    private final String description;

    TelemetryMetricType(String description) {
        this.description = description;
    }
}
