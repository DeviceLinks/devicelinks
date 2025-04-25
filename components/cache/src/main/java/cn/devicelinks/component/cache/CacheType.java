package cn.devicelinks.component.cache;

/**
 * 缓存类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum CacheType {
    /**
     * 仅开启Caffeine本地缓存
     */
    Caffeine,
    /**
     * 仅开启Redis分布式缓存
     */
    Redis,
    /**
     * 开启复合多级缓存
     * <p>
     * Caffeine => L1
     * Redis => L2
     */
    Composite
}
