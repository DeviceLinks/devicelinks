package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.Product;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link Product} Cache Support
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class ProductCache extends CacheSupport<ProductCacheKey, Product> {
    public ProductCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.PRODUCT_CACHE);
    }
}
