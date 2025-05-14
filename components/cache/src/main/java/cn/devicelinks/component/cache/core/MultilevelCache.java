package cn.devicelinks.component.cache.core;

import cn.devicelinks.component.cache.config.MultilevelCacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多级缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class MultilevelCache implements org.springframework.cache.Cache {

    protected final String cacheName;
    protected List<Cache<String, Object>> caches;
    private final ConcurrentMap<String, ReentrantLock> keyLocks = new ConcurrentHashMap<>();

    public MultilevelCache(MultilevelCacheConfig cacheConfig, String cacheName, RedisTemplate<String, Object> cacheRedisTemplate) {
        this.cacheName = cacheName;
        // @formatter:off
        this.caches = new ArrayList<>() {
            {
                // Caffeine => L1
                add(new CaffeineCache<>(cacheConfig.getCaffeineConfig(), cacheName));
                // Redis => L2
                add(new RedisCache<>(cacheConfig.getRedisConfig(), cacheName, cacheRedisTemplate));
            }
        };
        // @formatter:on
        this.caches.sort(Comparator.comparing(Cache::getOrder));
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public Object getNativeCache() {
        return this.caches;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = get(key, () -> null);
        if (value != null) return () -> value;
        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = get(key);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type");
        }
        return (T) ((value instanceof ValueWrapper) ? ((ValueWrapper) value).get() : value);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T value = (T) this.getCacheLevelByLevel(this.convertKey(key));
        if (value != null) {
            return value;
        }
        ReentrantLock lock = keyLocks.computeIfAbsent(this.convertKey(key), k -> new ReentrantLock());
        lock.lock();
        try {
            value = (T) this.getCacheLevelByLevel(this.convertKey(key));
            if (value != null) {
                return value;
            }
            // All cache misses, get from cache loader
            T loadedValue = valueLoader.call();
            if (loadedValue != null) {
                this.put(key, loadedValue);
            }
            return loadedValue;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            lock.unlock();
            keyLocks.remove(key);
        }
    }

    @Override
    public void put(Object key, Object value) {
        // Update cache data in reverse order
        List<Cache<String, Object>> reversedList = new ArrayList<>(caches);
        Collections.reverse(reversedList);
        reversedList.forEach(cache -> cache.put(this.convertKey(key), value));
    }

    @Override
    public void evict(Object key) {
        caches.forEach(cache -> cache.evict(this.convertKey(key)));
    }

    @Override
    public void clear() {
        caches.forEach(Cache::clear);
    }

    private String convertKey(Object key) {
        return String.valueOf(key);
    }

    private Object getCacheLevelByLevel(String key) {
        for (int i = 0; i < caches.size(); i++) {
            Object value = caches.get(i).get(key);
            if (value != null) {
                log.debug("Use cache data, Key: {}, cache level: {}.", key, (i + 1));
                // Backfill in reverse order
                for (int j = 0; j < i; j++) {
                    log.debug("Reverse update cache, Key: {}, get data level: {}, update target level: {}.", key, (i + 1), (j + 1));
                    caches.get(j).put(key, value);
                }
                return value;
            }
        }
        return null;
    }
}
