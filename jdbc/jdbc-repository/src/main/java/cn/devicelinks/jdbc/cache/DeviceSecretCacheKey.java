package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * The {@link cn.devicelinks.entity.DeviceSecret} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSecretCacheKey implements CacheKey {
    private String deviceSecretId;
    private String deviceId;

    @Override
    public void setId(String id) {
        this.deviceSecretId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        return deviceSecretId;
    }
}
