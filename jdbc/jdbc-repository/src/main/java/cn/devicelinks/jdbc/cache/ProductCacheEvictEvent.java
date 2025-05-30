package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.Product;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link Product} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class ProductCacheEvictEvent {
    private String productId;
    private String productKey;
    private Product savedProduct;
}
