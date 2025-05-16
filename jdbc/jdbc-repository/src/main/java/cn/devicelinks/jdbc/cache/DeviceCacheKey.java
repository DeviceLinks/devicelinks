package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * The {@link cn.devicelinks.entity.Device} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCacheKey implements CacheKey {
    private String deviceId;
    private String deviceName;

    @Override
    public void setId(String id) {
        this.deviceId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(deviceId) && !ObjectUtils.isEmpty(deviceName)) {
            return deviceId + "_" + deviceName;
        } else if (!ObjectUtils.isEmpty(deviceId) && ObjectUtils.isEmpty(deviceName)) {
            return deviceId;
        } else {
            return deviceName;
        }
    }
}
