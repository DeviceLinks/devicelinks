package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.Attribute;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link Attribute} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class AttributeCacheEvictEvent {
    private String attributeId;
    private String identifier;
    private Attribute savedAttribute;
}
