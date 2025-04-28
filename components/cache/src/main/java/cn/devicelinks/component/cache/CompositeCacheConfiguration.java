package cn.devicelinks.component.cache;

import cn.devicelinks.component.cache.core.Cache;
import cn.devicelinks.component.cache.core.CaffeineCache;
import cn.devicelinks.component.cache.core.CompositeCache;
import cn.devicelinks.component.cache.core.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static cn.devicelinks.component.cache.CacheRedisTemplateConfiguration.REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME;

/**
 * 复合多级缓存配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
@Import(CacheRedisTemplateConfiguration.class)
public class CompositeCacheConfiguration {

    public static final String COMPOSITE_CACHE_BEAN_NAME = "compositeCache";

    private final CacheProperties cacheProperties;

    public CompositeCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = COMPOSITE_CACHE_BEAN_NAME)
    public Cache<String, Object> compositeCache(@Qualifier(REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME) RedisTemplate<String, Object> cacheRedisTemplate) {
        CacheProperties.CacheCaffeineConfig caffeineConfig = cacheProperties.getCaffeine();
        CacheProperties.CacheRedisConfig redisConfig = cacheProperties.getRedis();
        return new CompositeCache<>(List.of(
                // L1 Cache
                new CaffeineCache<>(caffeineConfig.getMaximumSize(), caffeineConfig.getTtlSeconds()),
                // L2 Cache
                new RedisCache<>(cacheRedisTemplate, redisConfig.getPrefix(), redisConfig.getTtlSeconds())
        ));
    }
}
