package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备预配置策略
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceProvisionStrategy {
    Disabled("已禁用"),
    AllowCreateDevice("允许创建设备");
    private final String description;

    DeviceProvisionStrategy(String description) {
        this.description = description;
    }
}
