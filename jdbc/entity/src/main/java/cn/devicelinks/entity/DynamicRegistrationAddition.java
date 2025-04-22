package cn.devicelinks.entity;

import lombok.Data;

/**
 * 设备动态注册附加信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DynamicRegistrationAddition {
    /**
     * 每日注册限额
     * <p>
     * 为0时表示不限额，取值范围：1~10000
     */
    private int dailyLimit;
    /**
     * 注册后是否启用
     */
    private boolean afterEnable;
}
