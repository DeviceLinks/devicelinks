package cn.devicelinks.component.cache.core;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 基于{@link CaffeineCache}实现的本地L1级别缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CaffeineCache<K, V> implements Cache<K, V> {

    private static final long DEFAULT_MAXIMUM_SIZE = 10_000L;

    private static final long DEFAULT_L1_TTL_SECONDS = 60L;

    private final com.github.benmanes.caffeine.cache.Cache<K, CacheValue<V>> cache;

    public CaffeineCache() {
        this(DEFAULT_MAXIMUM_SIZE);
    }

    public CaffeineCache(long maximumSize) {
        this(maximumSize, DEFAULT_L1_TTL_SECONDS);
    }

    public CaffeineCache(long maximumSize, long ttlSeconds) {
        // @formatter:off
        this.cache = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterWrite(ttlSeconds, TimeUnit.SECONDS)
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
        return this.get(key, DEFAULT_L1_TTL_SECONDS, loader);
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
        this.put(key, value, DEFAULT_L1_TTL_SECONDS);
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
}
