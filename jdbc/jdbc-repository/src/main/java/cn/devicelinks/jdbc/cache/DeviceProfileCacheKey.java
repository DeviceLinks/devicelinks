package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.entity.DeviceProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * The {@link DeviceProfile} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceProfileCacheKey implements CacheKey {
    private String deviceProfileId;
    private String provisionKey;

    @Override
    public void setId(String id) {
        this.deviceProfileId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(provisionKey)) {
            return provisionKey;
        }
        return deviceProfileId;
    }
}
