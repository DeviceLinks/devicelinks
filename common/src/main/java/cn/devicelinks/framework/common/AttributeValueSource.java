package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 属性值来源
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum AttributeValueSource {
    DeviceReport("设备上报"),
    ServerSet("服务端设置"),
    SystemSet("系统设置");
    private final String description;

    AttributeValueSource(String description) {
        this.description = description;
    }
}
