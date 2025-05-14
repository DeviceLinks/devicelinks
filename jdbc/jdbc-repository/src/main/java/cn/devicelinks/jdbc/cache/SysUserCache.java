package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.SysUser;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link SysUser} Cache
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class SysUserCache extends CacheSupport<SysUserCacheKey, SysUser> {

    public SysUserCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.USER_CACHE);
    }
}
