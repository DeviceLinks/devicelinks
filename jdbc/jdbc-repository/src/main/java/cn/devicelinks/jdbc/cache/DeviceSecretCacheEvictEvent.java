package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.DeviceSecret;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link cn.devicelinks.entity.DeviceSecret} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class DeviceSecretCacheEvictEvent {
    private String deviceSecretId;
    private String deviceId;
    private DeviceSecret savedDeviceSecret;
}
