package cn.devicelinks.component.cache.core;

import java.io.Serializable;

/**
 * 缓存Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface CacheKey extends Serializable {
    /**
     * 设置通过ID来构建换成Key的方法
     *
     * @param id ID
     */
    void setId(String id);
}
