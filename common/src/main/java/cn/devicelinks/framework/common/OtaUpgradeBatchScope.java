package cn.devicelinks.framework.common;

/**
 * OTA升级批次升级范围
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum OtaUpgradeBatchScope {
    /**
     * 全部设备
     */
    all,
    /**
     * 定向升级
     */
    direction,
    /**
     * 区域升级
     */
    area,
    /**
     * 灰度升级
     */
    grayscale,
    /**
     * 分组升级
     */
    group
}
