package cn.devicelinks.component.cache.core;

import cn.devicelinks.component.cache.config.CaffeineCacheConfig;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 基于{@link CaffeineCache}实现的本地L1级别缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CaffeineCache<K, V> implements Cache<K, V> {

    private static final int L1_ORDER = 1;

    private final com.github.benmanes.caffeine.cache.Cache<K, CacheValue<V>> cache;

    private final CaffeineCacheConfig config;

    public CaffeineCache() {
        this(CaffeineCacheConfig.useDefault());
    }

    public CaffeineCache(CaffeineCacheConfig config) {
        this.config = config;
        // @formatter:off
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(config.getTtl(), config.getTtlTimeUnit())
                .build();
        // @formatter:on
    }

    @Override
    public V get(K key) {
        CacheValue<V> cacheValue = cache.getIfPresent(key);
        if (cacheValue == null || cacheValue.isExpired()) {
            cache.invalidate(key);
            return null;
        }
        return cacheValue.value;
    }

    @Override
    public V get(K key, CacheLoader<K, V> loader) {
        return this.get(key, config.getTtlTimeUnit().toSeconds(config.getTtl()), loader);
    }

    @Override
    public V get(K key, long ttlSeconds, CacheLoader<K, V> loader) {
        CacheValue<V> cacheValue = cache.get(key, k -> {
            V loadedValue = loader.load(k);
            return new CacheValue<>(loadedValue, ttlSeconds);
        });
        if (cacheValue == null || cacheValue.isExpired()) {
            cache.invalidate(key);
            return null;
        }
        return cacheValue.value;
    }

    @Override
    public void put(K key, V value) {
        this.put(key, value, config.getTtlTimeUnit().toSeconds(config.getTtl()));
    }

    @Override
    public void put(K key, V value, long ttlSeconds) {
        cache.put(key, new CacheValue<>(value, ttlSeconds));
    }

    @Override
    public void remove(K key) {
        cache.invalidate(key);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public int getOrder() {
        return L1_ORDER;
    }
}
