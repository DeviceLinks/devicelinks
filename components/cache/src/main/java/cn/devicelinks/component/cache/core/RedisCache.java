package cn.devicelinks.component.cache.core;

import cn.devicelinks.common.Constants;
import cn.devicelinks.component.cache.config.RedisCacheConfig;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Set;

/**
 * Redis L2 分布式缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private static final String DEFAULT_CACHE_NAME = "default";
    private static final String DEFAULT_REDIS_CACHE_KEY_PREFIX = "devicelinks:cache";
    private static final String KEY_MATCH_IDENTIFIER = "*";
    private static final int REDIS_CACHE_LEVEL = 2;

    @Getter
    protected final String cacheName;
    private final RedisCacheConfig config;
    private final RedisTemplate<String, V> redisTemplate;

    public RedisCache(RedisCacheConfig config, RedisTemplate<String, V> redisTemplate) {
        this(config, DEFAULT_CACHE_NAME, redisTemplate);
    }

    public RedisCache(RedisCacheConfig config, String cacheName, RedisTemplate<String, V> redisTemplate) {
        this.cacheName = cacheName;
        this.config = config;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(formatCacheKey(key));
    }

    @Override
    public void put(K key, V value) {
        if (value != null) {
            redisTemplate.opsForValue().set(formatCacheKey(key), value, config.getTtl(), config.getTtlTimeUnit());
        }
    }

    @Override
    public void putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null && value != null) {
            put(key, value);
        }
    }

    @Override
    public void evict(K key) {
        redisTemplate.delete(formatCacheKey(key));
    }

    @Override
    public void evict(Collection<K> keys) {
        redisTemplate.delete(keys.stream().map(this::formatCacheKey).toList());
    }

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(getCachePrefix() + KEY_MATCH_IDENTIFIER);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public int getOrder() {
        return REDIS_CACHE_LEVEL;
    }

    private String formatCacheKey(K key) {
        return getCachePrefix() + Constants.RISK + cacheName + Constants.RISK + key;
    }

    private String getCachePrefix() {
        return !ObjectUtils.isEmpty(config.getPrefix()) ? config.getPrefix() : DEFAULT_REDIS_CACHE_KEY_PREFIX;
    }
}
