package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备配置文件批量设置方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceProfileBatchSetAway {
    SpecifyDevice("指定设备"),
    DeviceTag("设备标签"),
    DeviceType("设备类型");
    private final String description;

    DeviceProfileBatchSetAway(String description) {
        this.description = description;
    }
}
