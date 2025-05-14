package cn.devicelinks.component.cache.core;

/**
 * 缓存数据封装接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface CacheValueWrapper<T> {

    T get();

    boolean isExpired();
}
