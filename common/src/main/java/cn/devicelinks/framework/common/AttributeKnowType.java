package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 属性知晓类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum AttributeKnowType {
    Known("已知属性", EnumShowStyle.Success),
    Unknown("未知属性", EnumShowStyle.Warning);
    private final String description;
    private final EnumShowStyle showStyle;

    AttributeKnowType(String description, EnumShowStyle showStyle) {
        this.description = description;
        this.showStyle = showStyle;
    }
}
