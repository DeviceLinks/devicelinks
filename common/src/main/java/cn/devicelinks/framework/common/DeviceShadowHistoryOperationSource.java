package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备影子操作来源
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceShadowHistoryOperationSource {
    DeviceReport("设备上报"),
    CloudIssue("云端下发"),
    SystemAuto("系统自动处理");
    private final String description;

    DeviceShadowHistoryOperationSource(String description) {
        this.description = description;
    }
}
