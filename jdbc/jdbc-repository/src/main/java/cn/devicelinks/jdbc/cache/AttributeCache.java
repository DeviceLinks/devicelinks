package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.Attribute;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link Attribute} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class AttributeCache extends CacheSupport<AttributeCacheKey, Attribute> {
    public AttributeCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.ATTRIBUTE_CACHE);
    }
}
