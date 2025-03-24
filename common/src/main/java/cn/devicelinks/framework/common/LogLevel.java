package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 日志等级
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum LogLevel {
    Debug("调试", EnumShowStyle.Default),
    Info("信息", EnumShowStyle.Processing),
    Warn("警告", EnumShowStyle.Warning),
    Error("错误", EnumShowStyle.Error);
    private final String description;
    private final EnumShowStyle showStyle;

    LogLevel(String description, EnumShowStyle showStyle) {
        this.description = description;
        this.showStyle = showStyle;
    }
}
