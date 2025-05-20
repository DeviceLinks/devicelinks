package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.DeviceCredentials;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link DeviceCredentials} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class DeviceCredentialsCacheEvictEvent {
    private String deviceCredentialsId;
    private String deviceId;
    private String token;
    private String tokenHash;
    private DeviceCredentials savedDeviceCredentials;
}
