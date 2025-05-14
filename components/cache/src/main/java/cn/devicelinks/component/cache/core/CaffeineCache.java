package cn.devicelinks.component.cache.core;

import cn.devicelinks.component.cache.config.CaffeineCacheConfig;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;

import java.util.Collection;

/**
 * Caffeine L1本地缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CaffeineCache<K, V> implements Cache<K, V> {

    private static final int CAFFEINE_CACHE_LEVEL = 1;
    private static final String DEFAULT_CACHE_NAME = "default";
    @Getter
    protected final String cacheName;
    private final com.github.benmanes.caffeine.cache.Cache<K, CacheValueWrapper<V>> cache;
    private final CaffeineCacheConfig config;

    public CaffeineCache(String cacheName) {
        this(CaffeineCacheConfig.useDefault(), cacheName);
    }

    public CaffeineCache() {
        this(CaffeineCacheConfig.useDefault(), DEFAULT_CACHE_NAME);
    }

    public CaffeineCache(CaffeineCacheConfig config) {
        this(config, DEFAULT_CACHE_NAME);
    }

    public CaffeineCache(CaffeineCacheConfig config, String cacheName) {
        this.cacheName = cacheName;
        this.config = config;
        // @formatter:off
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(config.getTtl(), config.getTtlTimeUnit())
                .build();
        // @formatter:on
    }

    @Override
    public V get(K key) {
        CacheValueWrapper<V> cacheValueWrapper = cache.getIfPresent(key);
        if (cacheValueWrapper == null || cacheValueWrapper.isExpired()) {
            cache.invalidate(key);
            return null;
        }
        return cacheValueWrapper.get();
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, SimpleCacheValueWrapper.wrap(value, config.getTtl(), config.getTtlTimeUnit()));
    }

    @Override
    public void putIfAbsent(K key, V value) {
        CacheValueWrapper<V> cacheValueWrapper = cache.getIfPresent(key);
        if (cacheValueWrapper == null) {
            put(key, value);
        }
    }

    @Override
    public void evict(K key) {
        cache.invalidate(key);
    }

    @Override
    public void evict(Collection<K> keys) {
        cache.invalidateAll(keys);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public int getOrder() {
        return CAFFEINE_CACHE_LEVEL;
    }
}
