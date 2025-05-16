package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.DeviceTag;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link DeviceTag} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceTagCache extends CacheSupport<DeviceTagCacheKey, DeviceTag> {
    public DeviceTagCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.DEVICE_TAG_CACHE);
    }
}
