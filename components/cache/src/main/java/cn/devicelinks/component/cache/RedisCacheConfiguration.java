package cn.devicelinks.component.cache;

import cn.devicelinks.component.cache.core.Cache;
import cn.devicelinks.component.cache.core.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import static cn.devicelinks.component.cache.CacheRedisTemplateConfiguration.REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME;

/**
 * Redis缓存实例化配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
public class RedisCacheConfiguration {
    public static final String REDIS_CACHE_BEAN_NAME = "redisCache";

    private final CacheProperties cacheProperties;

    public RedisCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = REDIS_CACHE_BEAN_NAME)
    public Cache<String, Object> caffeineCache(@Qualifier(REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME) RedisTemplate<String, Object> cacheRedisTemplate) {
        CacheProperties.CacheRedisConfig redisConfig = cacheProperties.getRedis();
        return new RedisCache<>(cacheRedisTemplate, redisConfig.getPrefix(), redisConfig.getTtlSeconds());
    }
}
