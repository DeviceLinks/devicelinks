package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.entity.FunctionModule;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * The {@link FunctionModule} Cache Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class FunctionModuleCache extends CacheSupport<FunctionModuleCacheKey, FunctionModule> {
    public FunctionModuleCache(CacheManager cacheManager) {
        super(cacheManager, CacheConstants.FUNCTION_MODULE_CACHE);
    }
}
