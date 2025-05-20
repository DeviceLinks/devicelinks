package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.DeviceSecret;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceSecretCache extends CacheSupport<DeviceSecretCacheKey, DeviceSecret> {
    public DeviceSecretCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.DEVICE_SECRET_CACHE);
    }
}
