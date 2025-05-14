package cn.devicelinks.component.cache.core;

import cn.devicelinks.common.Constants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.cache.Cache;

import java.util.concurrent.TimeUnit;

/**
 * The {@link CacheValueWrapper} Simple Class
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleCacheValueWrapper<T> implements CacheValueWrapper<T> {
    private final T value;
    private final long expireAt;

    @Override
    public T get() {
        return this.value;
    }

    public static <T> SimpleCacheValueWrapper<T> empty() {
        return new SimpleCacheValueWrapper<>(null, -1);
    }

    public static <T> SimpleCacheValueWrapper<T> wrap(T value, long expireTtl, TimeUnit expireTtlUnit) {
        long expireAt = System.currentTimeMillis() + expireTtlUnit.toMillis(expireTtl);
        return new SimpleCacheValueWrapper<>(value, expireAt);
    }

    public static <T> SimpleCacheValueWrapper<T> wrap(T value) {
        return new SimpleCacheValueWrapper<>(value, -1);
    }

    @SuppressWarnings("unchecked")
    public static <T> SimpleCacheValueWrapper<T> wrap(Cache.ValueWrapper source) {
        return source == null ? null : new SimpleCacheValueWrapper<>((T) source.get(), -1);
    }

    @Override
    public boolean isExpired() {
        return expireAt > Constants.ZERO && System.currentTimeMillis() > expireAt;
    }
}
