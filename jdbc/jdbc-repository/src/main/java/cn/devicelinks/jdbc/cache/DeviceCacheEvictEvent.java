package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.Device;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link cn.devicelinks.entity.Device} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class DeviceCacheEvictEvent {
    private String deviceId;
    private String deviceName;
    private Device savedDevice;
}
