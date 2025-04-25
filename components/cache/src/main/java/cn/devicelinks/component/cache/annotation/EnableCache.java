package cn.devicelinks.component.cache.annotation;

import cn.devicelinks.component.cache.CacheType;
import cn.devicelinks.component.cache.EnableCacheImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用缓存注解
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableCacheImportSelector.class)
public @interface EnableCache {
    /**
     * 开启缓存的类型
     *
     * @return {@link CacheType}
     */
    CacheType cacheType() default CacheType.Caffeine;
}
