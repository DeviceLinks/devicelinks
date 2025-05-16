package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * The {@link cn.devicelinks.entity.DeviceAttribute}
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeCacheKey implements CacheKey {
    private String attributeId;
    private String identifier;

    @Override
    public void setId(String id) {
        this.attributeId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(attributeId)) {
            return attributeId;
        }
        return identifier;
    }
}
