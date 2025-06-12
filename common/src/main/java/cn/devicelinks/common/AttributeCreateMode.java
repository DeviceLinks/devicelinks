package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备属性创建模型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum AttributeCreateMode {
    Predefined("预定义"),
    Dynamic("动态");
    private final String description;

    AttributeCreateMode(String description) {
        this.description = description;
    }
}
