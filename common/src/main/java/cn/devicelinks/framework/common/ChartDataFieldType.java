package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 数据图表字段类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum ChartDataFieldType {
    Attribute("设备属性"),
    Telemetry("遥测数据");
    private final String description;

    ChartDataFieldType(String description) {
        this.description = description;
    }
}
