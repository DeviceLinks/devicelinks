package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 更新设备启用状态动作
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum UpdateDeviceEnabledAction {
    Enable("启用"),
    Disable("禁用");
    private final String description;

    UpdateDeviceEnabledAction(String description) {
        this.description = description;
    }
}
