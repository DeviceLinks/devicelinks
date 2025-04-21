package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 期望属性状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DesiredAttributeStatus {
    Pending("等待", EnumShowStyle.Warning),
    Confirmed("已确认", EnumShowStyle.Success),
    Timeout("超时", EnumShowStyle.Error),
    Exception("异常", EnumShowStyle.Error);

    private final String description;
    private final EnumShowStyle showStyle;

    DesiredAttributeStatus(String description, EnumShowStyle showStyle) {
        this.description = description;
        this.showStyle = showStyle;
    }
}
