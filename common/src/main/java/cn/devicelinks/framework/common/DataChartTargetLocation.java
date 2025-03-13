package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 数据图表数据目标位置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DataChartTargetLocation {
    DeviceStatus("设备状态");

    private final String description;

    DataChartTargetLocation(String description) {
        this.description = description;
    }
}
