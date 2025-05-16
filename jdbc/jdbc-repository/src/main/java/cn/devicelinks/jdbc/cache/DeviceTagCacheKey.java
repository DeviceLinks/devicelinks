package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * The {@link cn.devicelinks.entity.DeviceTag} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTagCacheKey implements CacheKey {
    private String tagId;
    private String name;

    @Override
    public void setId(String id) {
        this.tagId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(name)) {
            return name;
        }
        return tagId;
    }
}
