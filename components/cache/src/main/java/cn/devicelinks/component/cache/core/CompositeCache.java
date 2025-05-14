package cn.devicelinks.component.cache.core;

import cn.devicelinks.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 复合多级数据缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class CompositeCache<K, V> implements Cache<K, V> {

    private static final String DEFAULT_CACHE_NAME = "default";
    protected final String cacheName;
    private final ConcurrentMap<K, ReentrantLock> keyLocks = new ConcurrentHashMap<>();
    private final List<Cache<K, V>> caches;

    public CompositeCache(List<Cache<K, V>> caches) {
        this(caches, DEFAULT_CACHE_NAME);
    }

    public CompositeCache(List<Cache<K, V>> caches, String cacheName) {
        Assert.notEmpty(caches, "Pass at least one level of cache instance.");
        this.cacheName = cacheName;
        this.caches = new ArrayList<>(caches);
        this.caches.sort(Comparator.comparing(Cache::getOrder));
    }

    @Override
    public String getCacheName() {
        return this.cacheName;
    }

    @Override
    public V get(K key) {
        return this.get(key, null);
    }

    @Override
    public V get(K key, Supplier<V> supplier, boolean putToCache) {
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
            if (supplier != null) {
                loadedValue = supplier.get();
                if (loadedValue != null && putToCache) {
                    this.put(key, loadedValue);
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
        // Update cache data in reverse order
        List<Cache<K, V>> reversedList = new ArrayList<>(caches);
        Collections.reverse(reversedList);
        reversedList.forEach(cache -> cache.put(key, value));
    }

    @Override
    public void putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            put(key, value);
        }
    }

    @Override
    public void evict(K key) {
        caches.forEach(cache -> cache.evict(key));
    }

    @Override
    public void evict(Collection<K> keys) {

    }

    @Override
    public void clear() {
        caches.forEach(Cache::clear);
    }

    private V getCacheLevelByLevel(K key) {
        for (int i = 0; i < caches.size(); i++) {
            V value = caches.get(i).get(key);
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

    @Override
    public int getOrder() {
        return Constants.ZERO;
    }
}
