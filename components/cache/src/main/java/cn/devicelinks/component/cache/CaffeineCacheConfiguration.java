package cn.devicelinks.component.cache;

import cn.devicelinks.component.cache.core.Cache;
import cn.devicelinks.component.cache.core.CaffeineCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Caffeine缓存实例化配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
public class CaffeineCacheConfiguration {

    public static final String CAFFEINE_CACHE_BEAN_NAME = "caffeineCache";

    private final CacheProperties cacheProperties;

    public CaffeineCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = CAFFEINE_CACHE_BEAN_NAME)
    public Cache<String, Object> caffeineCache() {
        CacheProperties.CacheCaffeineConfig caffeineConfig = cacheProperties.getCaffeine();
        return new CaffeineCache<>(caffeineConfig.getMaximumSize(), caffeineConfig.getTtlSeconds());
    }
}
