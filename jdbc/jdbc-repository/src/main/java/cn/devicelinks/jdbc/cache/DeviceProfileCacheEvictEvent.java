package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.DeviceProfile;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link DeviceProfile} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class DeviceProfileCacheEvictEvent {
    private String deviceProfileId;
    private DeviceProfile savedDeviceProfile;
}
