package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.SysDepartment;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link SysDepartment} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class SysDepartmentCache extends CacheSupport<SysDepartmentCacheKey, SysDepartment> {
    public SysDepartmentCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.DEPARTMENT_CACHE);
    }
}
