package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 数据图表类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DataChartType {
    Line("折线图"),
    Bar("柱状图"),
    Pie("饼图"),
    Dashboard("仪表盘"),
    WaterWaves("水波图"),
    BasicArea("基础面积图");
    private final String description;

    DataChartType(String description) {
        this.description = description;
    }
}
