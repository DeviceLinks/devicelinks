package cn.devicelinks.component.cache.core;

/**
 * 缓存对象
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CacheValue<V> {
    final V value;
    final long expireAt; // 毫秒

    CacheValue(V value, long ttlSeconds) {
        this.value = value;
        this.expireAt = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expireAt;
    }
}
