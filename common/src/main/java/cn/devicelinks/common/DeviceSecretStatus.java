package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceSecretStatus {
    Active("有效"),
    Expired("已过期");
    private final String description;

    DeviceSecretStatus(String description) {
        this.description = description;
    }
}
