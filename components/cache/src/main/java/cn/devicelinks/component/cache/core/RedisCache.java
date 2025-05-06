package cn.devicelinks.component.cache.core;

import cn.devicelinks.common.Constants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis实现的分布式L2级别缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private static final String DEFAULT_REDIS_CACHE_KEY_PREFIX = "devicelinks:cache:";

    private static final String KEY_MATCH_IDENTIFIER = "*";

    private static final long DEFAULT_L2_TTL_SECONDS = 10 * 60L;

    private static final int L2_ORDER = 2;

    private final RedisTemplate<String, V> redisTemplate;

    private String cachePrefix;

    private long ttlSeconds;

    public RedisCache(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCache(RedisTemplate<String, V> redisTemplate, String cachePrefix) {
        this.redisTemplate = redisTemplate;
        this.cachePrefix = cachePrefix;
    }

    public RedisCache(RedisTemplate<String, V> redisTemplate, String cachePrefix, long ttlSeconds) {
        this.redisTemplate = redisTemplate;
        this.cachePrefix = cachePrefix;
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(formatCacheKey(key));
    }

    @Override
    public V get(K key, CacheLoader<K, V> loader) {
        return this.get(key, getTtlSeconds(), loader);
    }

    @Override
    public V get(K key, long ttlSeconds, CacheLoader<K, V> loader) {
        String formatKey = this.formatCacheKey(key);
        V value = redisTemplate.opsForValue().get(formatKey);
        if (value == null && loader != null) {
            V loadedValue = loader.load(key);
            redisTemplate.opsForValue().set(formatKey, loadedValue, ttlSeconds, TimeUnit.SECONDS);
            return loadedValue;
        }
        return value;
    }

    @Override
    public void put(K key, V value) {
        this.put(key, value, getTtlSeconds());
    }

    @Override
    public void put(K key, V value, long ttlSeconds) {
        redisTemplate.opsForValue().set(formatCacheKey(key), value, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void remove(K key) {
        redisTemplate.delete(formatCacheKey(key));
    }

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(getCachePrefix() + KEY_MATCH_IDENTIFIER);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String formatCacheKey(K key) {
        return getCachePrefix() + Constants.RISK + key;
    }

    private String getCachePrefix() {
        return !ObjectUtils.isEmpty(cachePrefix) ? cachePrefix : DEFAULT_REDIS_CACHE_KEY_PREFIX;
    }

    private long getTtlSeconds() {
        return ttlSeconds <= Constants.ZERO ? DEFAULT_L2_TTL_SECONDS : ttlSeconds;
    }

    @Override
    public int getOrder() {
        return L2_ORDER;
    }
}
