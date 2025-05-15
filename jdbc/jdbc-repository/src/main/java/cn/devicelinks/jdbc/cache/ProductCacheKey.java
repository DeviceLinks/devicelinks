package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link Product} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCacheKey implements CacheKey {
    private String productId;

    @Override
    public void setId(String id) {
        this.productId = id;
    }
}
