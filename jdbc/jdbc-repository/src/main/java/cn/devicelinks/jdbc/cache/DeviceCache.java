package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.Device;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link Device} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceCache extends CacheSupport<DeviceCacheKey, Device> {
    public DeviceCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.DEVICE_CACHE);
    }
}
