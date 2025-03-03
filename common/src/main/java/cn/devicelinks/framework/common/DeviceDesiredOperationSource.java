package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备期望操作来源
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceDesiredOperationSource {
    CloudIssue("云端下发"),
    User("用户操作"),
    AutomaticRule("规则自动触发");
    private final String description;

    DeviceDesiredOperationSource(String description) {
        this.description = description;
    }
}
