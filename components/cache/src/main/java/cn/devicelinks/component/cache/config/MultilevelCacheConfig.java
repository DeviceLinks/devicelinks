package cn.devicelinks.component.cache.config;

import lombok.Getter;

/**
 * 多级缓存配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class MultilevelCacheConfig {
    private final CaffeineCacheConfig caffeineConfig;
    private final RedisCacheConfig redisConfig;

    public MultilevelCacheConfig(CaffeineCacheConfig caffeineConfig, RedisCacheConfig redisConfig) {
        this.caffeineConfig = caffeineConfig;
        this.redisConfig = redisConfig;
    }
}
