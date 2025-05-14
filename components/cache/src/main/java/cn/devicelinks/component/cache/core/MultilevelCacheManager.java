package cn.devicelinks.component.cache.core;

import cn.devicelinks.component.cache.config.MultilevelCacheConfig;
import lombok.NonNull;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;

/**
 * The {@link MultilevelCache} Manager
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class MultilevelCacheManager extends AbstractCacheManager {
    private final MultilevelCacheConfig cacheConfig;
    private final RedisTemplate<String, Object> cacheRedisTemplate;

    public MultilevelCacheManager(MultilevelCacheConfig cacheConfig, RedisTemplate<String, Object> cacheRedisTemplate) {
        this.cacheConfig = cacheConfig;
        this.cacheRedisTemplate = cacheRedisTemplate;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        // No preload cache required
        return List.of();
    }

    @Override
    protected Cache getMissingCache(@NonNull String cacheName) {
        return new MultilevelCache(cacheConfig, cacheName, cacheRedisTemplate);
    }
}
