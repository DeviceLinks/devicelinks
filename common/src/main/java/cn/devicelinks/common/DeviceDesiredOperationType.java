package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备期望操作类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceDesiredOperationType {
    Set("设置"),
    Clear("清除"),
    Merge("合并");
    private final String description;

    DeviceDesiredOperationType(String description) {
        this.description = description;
    }
}
