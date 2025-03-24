package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 预注册策略
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum ProvisionRegistrationStrategy {
    Disabled("禁用"),
    AllowCreateDevice("允许创建设备"),
    CheckDevice("检查预注册设备"),
    X509("X.509证书");
    private final String description;

    ProvisionRegistrationStrategy(String description) {
        this.description = description;
    }
}
