package cn.devicelinks.component.cache.core;

/**
 * 缓存接口，定义了缓存的基本操作。
 *
 * @param <K> 缓存键的类型
 * @param <V> 缓存值的类型
 */
public interface Cache<K, V> {
    /**
     * 根据指定的键获取缓存中的值。
     *
     * @param key 要查找的键
     * @return 返回与键关联的值，如果键不存在则返回null
     */
    V get(K key);

    /**
     * 根据指定的键获取缓存中的值。如果键不存在，则使用提供的{@link CacheLoader}加载值并存入缓存。
     *
     * @param key    要查找的键
     * @param loader 用于加载值的{@link CacheLoader}
     * @return 返回与键关联的值，如果键不存在则通过{@link CacheLoader}加载并返回
     */
    V get(K key, CacheLoader<K, V> loader);

    /**
     * 根据指定的键获取缓存中的值。如果键不存在，则使用提供的{@link CacheLoader}加载值并存入缓存，并设置缓存的生存时间（TTL）。
     *
     * @param key        要查找的键
     * @param ttlSeconds 缓存的生存时间（以秒为单位）
     * @param loader     用于加载值的{@link CacheLoader}
     * @return 返回与键关联的值，如果键不存在则通过{@link CacheLoader}加载并返回
     */
    V get(K key, long ttlSeconds, CacheLoader<K, V> loader);

    /**
     * 将指定的键值对存入缓存。
     *
     * @param key   要存入的键
     * @param value 要存入的值
     */
    void put(K key, V value);

    /**
     * 将指定的键值对存入缓存，并设置缓存的生存时间（TTL）。
     *
     * @param key        要存入的键
     * @param value      要存入的值
     * @param ttlSeconds 缓存的生存时间（以秒为单位）
     */
    void put(K key, V value, long ttlSeconds);

    /**
     * 从缓存中移除指定的键及其关联的值。
     *
     * @param key 要移除的键
     */
    void remove(K key);

    /**
     * 清空缓存中的所有键值对。
     */
    void clear();
}

