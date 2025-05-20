package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.entity.DeviceCredentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * The {@link DeviceCredentials} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialsCacheKey implements CacheKey {
    private String deviceCredentialId;
    private String deviceId;
    private String token;
    private String tokenHash;

    @Override
    public void setId(String id) {
        this.deviceCredentialId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(deviceId)) {
            return deviceId;
        } else if (!ObjectUtils.isEmpty(token)) {
            return token;
        } else if (!ObjectUtils.isEmpty(tokenHash)) {
            return tokenHash;
        }
        return deviceCredentialId;
    }
}
