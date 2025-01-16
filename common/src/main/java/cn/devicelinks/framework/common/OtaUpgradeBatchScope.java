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
    All,
    /**
     * 定向升级
     */
    Direction,
    /**
     * 区域升级
     */
    Area,
    /**
     * 灰度升级
     */
    Grayscale,
    /**
     * 分组升级
     */
    Group
}
