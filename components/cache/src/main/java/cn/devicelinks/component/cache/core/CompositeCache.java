package cn.devicelinks.component.cache.core;

import cn.devicelinks.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 复合多级数据缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class CompositeCache<K, V> implements Cache<K, V> {

    private static final long DEFAULT_TTL_SECONDS = 300;

    private final ConcurrentMap<K, ReentrantLock> keyLocks = new ConcurrentHashMap<>();

    private final List<Cache<K, V>> caches;

    private final long ttlSeconds;

    public CompositeCache(List<Cache<K, V>> cacheLevels) {
        this(cacheLevels, DEFAULT_TTL_SECONDS);
    }

    public CompositeCache(List<Cache<K, V>> caches, long ttlSeconds) {
        Assert.notEmpty(caches, "Pass at least one level of cache instance.");
        this.caches = caches;
        this.caches.sort(Comparator.comparing(Cache::getOrder));
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public V get(K key) {
        return this.get(key, null);
    }

    @Override
    public V get(K key, CacheLoader<K, V> loader) {
        return this.get(key, this.ttlSeconds, loader);
    }

    @Override
    public V get(K key, long ttlSeconds, CacheLoader<K, V> loader) {
        V value = this.getCacheLevelByLevel(key);
        if (value != null) {
            return value;
        }
        ReentrantLock lock = keyLocks.computeIfAbsent(key, k -> new ReentrantLock());
        lock.lock();
        try {
            value = this.getCacheLevelByLevel(key);
            if (value != null) {
                return value;
            }
            // All cache misses, get from cache loader
            V loadedValue = null;
            if (loader != null) {
                loadedValue = loader.load(key);
                if (loadedValue != null) {
                    this.put(key, loadedValue, ttlSeconds);
                }
            }
            return loadedValue;
        } finally {
            lock.unlock();
            keyLocks.remove(key);
        }
    }

    @Override
    public void put(K key, V value) {
        this.put(key, value, this.ttlSeconds);
    }

    @Override
    public void put(K key, V value, long ttlSeconds) {
        // Update cache data in reverse order
        List<Cache<K, V>> reversedList = new ArrayList<>(caches);
        Collections.reverse(reversedList);
        reversedList.forEach(cache -> cache.put(key, value, ttlSeconds));
    }

    @Override
    public void remove(K key) {
        caches.forEach(cache -> cache.remove(key));
    }

    @Override
    public void clear() {
        caches.forEach(Cache::clear);
    }

    private V getCacheLevelByLevel(K key) {
        for (int i = 0; i < caches.size(); i++) {
            V value = caches.get(i).get(key);
            if (value != null) {
                // Backfill in reverse order
                for (int j = 0; j < i; j++) {
                    caches.get(j).put(key, value, ttlSeconds);
                }
                return value;
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Constants.ZERO;
    }
}
