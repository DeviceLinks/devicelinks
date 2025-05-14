package cn.devicelinks.component.cache.core;

import cn.devicelinks.common.Constants;
import org.springframework.cache.CacheManager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * The {@link Cache} Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CacheSupport<K extends CacheKey, V extends Serializable> implements Cache<K, V> {

    private final String cacheName;
    private final org.springframework.cache.Cache cache; // MultilevelCache Instance

    public CacheSupport(CacheManager cacheManager, String cacheName) {
        this.cacheName = cacheName;
        this.cache = Optional.ofNullable(cacheManager.getCache(cacheName))
                .orElseThrow(() -> new IllegalArgumentException("Cache '" + cacheName + "' is not configured"));
    }

    @Override
    public String getCacheName() {
        return this.cacheName;
    }

    @Override
    public V get(K key) {
        org.springframework.cache.Cache.ValueWrapper valueWrapper = this.cache.get(key);
        return valueWrapper != null ? (V) valueWrapper.get() : null;
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
    }

    @Override
    public void putIfAbsent(K key, V value) {
        this.cache.putIfAbsent(key, value);
    }

    @Override
    public void evict(K key) {
        this.cache.evict(key);
    }

    @Override
    public void evict(Collection<K> keys) {
        keys.forEach(this.cache::evict);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public int getOrder() {
        return Constants.ZERO;
    }
}
