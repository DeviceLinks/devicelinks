package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.DeviceCredentials;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link DeviceCredentials} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceCredentialsCache extends CacheSupport<DeviceCredentialsCacheKey, DeviceCredentials> {
    public DeviceCredentialsCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.DEVICE_CREDENTIALS_CACHE);
    }
}
