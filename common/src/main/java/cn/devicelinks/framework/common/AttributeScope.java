package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 属性范围
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum AttributeScope {
    Device("设备端属性"),
    Server("服务端属性"),
    Common("公共属性");
    private final String description;

    AttributeScope(String description) {
        this.description = description;
    }
}
