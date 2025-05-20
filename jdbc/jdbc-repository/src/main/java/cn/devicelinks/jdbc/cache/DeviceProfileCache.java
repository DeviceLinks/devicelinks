package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.DeviceProfile;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link DeviceProfile} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceProfileCache extends CacheSupport<DeviceProfileCacheKey, DeviceProfile> {
    public DeviceProfileCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.DEVICE_PROFILE_CACHE);
    }
}
