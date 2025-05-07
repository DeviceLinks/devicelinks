package cn.devicelinks.component.cache;

import cn.devicelinks.common.exception.DeviceLinksException;
import cn.devicelinks.component.cache.annotation.EnableCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 启用不同类型的缓存选择器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class EnableCacheImportSelector implements ImportSelector {
    private static final String CACHE_TYPE_ATTRIBUTE_NAME = "cacheType";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableCache.class.getName());
        CacheType cacheType = (CacheType) annotationAttributes.get(CACHE_TYPE_ATTRIBUTE_NAME);
        log.info("Enable data caching, cache type: [{}].", cacheType);
        switch (cacheType) {
            case Caffeine:
                return new String[]{CaffeineCacheConfiguration.class.getName()};
            case Redis:
                return new String[]{RedisCacheConfiguration.class.getName()};
            case Composite:
                return new String[]{CompositeCacheConfiguration.class.getName()};
            default:
                throw new DeviceLinksException("Unsupported CacheType：" + cacheType);
        }
    }
}
