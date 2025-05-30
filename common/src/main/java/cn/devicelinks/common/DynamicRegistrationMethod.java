package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 动态注册方式
 *
 * @author 恒宇少年
 * @since 1.0
 */

@ApiEnum
@Getter
public enum DynamicRegistrationMethod {
    /**
     * 预配置
     */
    ProvisionKey("预配置"),
    /**
     * 产品密钥
     */
    ProductKey("产品密钥");

    private final String description;

    DynamicRegistrationMethod(String description) {
        this.description = description;
    }

    /**
     * 根据名称获取对应的枚举值，如果没有匹配则返回 null
     *
     * @param name 枚举名称
     * @return 对应枚举值或 null
     */
    public static DynamicRegistrationMethod fromName(String name) {
        for (DynamicRegistrationMethod method : values()) {
            if (method.name().equalsIgnoreCase(name)) {
                return method;
            }
        }
        return null;
    }
}
