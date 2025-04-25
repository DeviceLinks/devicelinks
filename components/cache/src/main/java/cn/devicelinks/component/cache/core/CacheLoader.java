package cn.devicelinks.component.cache.core;

/**
 * 缓存数据加载接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface CacheLoader<K, V> {
    /**
     * 加载缓存数据
     *
     * @param key 缓存数据Key
     * @return 加载到的缓存数据
     */
    V load(K key);
}
