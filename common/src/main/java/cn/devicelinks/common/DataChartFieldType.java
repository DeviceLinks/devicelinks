package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 数据图表字段类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DataChartFieldType {
    Attribute("设备属性"),
    Telemetry("遥测数据");
    private final String description;

    DataChartFieldType(String description) {
        this.description = description;
    }
}
