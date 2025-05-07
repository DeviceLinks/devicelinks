package cn.devicelinks.component.cache.spring;

import cn.devicelinks.component.cache.CacheProperties;
import cn.devicelinks.component.cache.config.CaffeineCacheConfig;
import cn.devicelinks.component.cache.config.RedisCacheConfig;
import cn.devicelinks.component.cache.core.CaffeineCache;
import cn.devicelinks.component.cache.core.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * The {@link SpringCompositeCache} Manager
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class SpringCompositeCacheManager extends AbstractCacheManager {

    private final CacheProperties cacheProperties;
    private final RedisTemplate<String, Object> cacheRedisTemplate;

    public SpringCompositeCacheManager(CacheProperties cacheProperties, RedisTemplate<String, Object> cacheRedisTemplate) {
        this.cacheProperties = cacheProperties;
        this.cacheRedisTemplate = cacheRedisTemplate;
    }

    @Override
    @NonNull
    protected Collection<? extends Cache> loadCaches() {
        // No preloading required
        return List.of();
    }

    @Override
    protected Cache getMissingCache(@NonNull String cacheName) {
        CaffeineCacheConfig caffeineConfig = cacheProperties.getCaffeine();
        RedisCacheConfig redisConfig = cacheProperties.getRedis();
        // Caffeine => L1
        CaffeineCache<String, Object> caffeineCache = new CaffeineCache<>(caffeineConfig);
        // Redis => L2
        RedisCache<String, Object> redisCache = new RedisCache<>(cacheRedisTemplate, redisConfig);
        return new SpringCompositeCache(cacheName, List.of(caffeineCache, redisCache));
    }
}
