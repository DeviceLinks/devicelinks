package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

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
    private String productKey;

    @Override
    public void setId(String id) {
        this.productId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(productKey)) {
            return productKey;
        }
        return productId;
    }
}
