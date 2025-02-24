package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备影子状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceShadowStatus {
    Normal("正常"),
    InSync("同步中"),
    Conflict("冲突"),
    Error("异常");

    private final String description;

    DeviceShadowStatus(String description) {
        this.description = description;
    }
}
