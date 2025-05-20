package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.DeviceTag;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link cn.devicelinks.entity.DeviceTag} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class DeviceTagCacheEvictEvent {
    private String tagId;
    private String name;
    private DeviceTag savedDeviceTag;
}
