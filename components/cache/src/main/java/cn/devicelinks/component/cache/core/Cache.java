package cn.devicelinks.component.cache.core;

import org.springframework.core.Ordered;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 缓存接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface Cache<K, V> extends Ordered {

    String getCacheName();

    V get(K key);

    default V get(K key, Supplier<V> supplier) {
        return get(key, supplier, true);
    }

    default V get(K key, Supplier<V> supplier, boolean putToCache) {
        return Optional.ofNullable(get(key))
                .orElseGet(() -> {
                    V value = supplier.get();
                    if (putToCache) {
                        put(key, value);
                    }
                    return value;
                });
    }

    void put(K key, V value);

    void putIfAbsent(K key, V value);

    void evict(K key);

    void evict(Collection<K> keys);

    void clear();
}
