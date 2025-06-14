package cn.devicelinks.component.cache;

import cn.devicelinks.component.cache.config.CaffeineCacheConfig;
import cn.devicelinks.component.cache.config.MultilevelCacheConfig;
import cn.devicelinks.component.cache.config.RedisCacheConfig;
import cn.devicelinks.component.cache.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
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
@EnableCaching // 启用Spring缓存
public class MultilevelCacheConfiguration {

    public static final String COMPOSITE_CACHE_BEAN_NAME = "multilevelCache";

    private final CacheProperties cacheProperties;

    public MultilevelCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = COMPOSITE_CACHE_BEAN_NAME)
    public Cache<String, Object> compositeCache(@Qualifier(REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME) RedisTemplate<String, Object> cacheRedisTemplate) {
        CaffeineCacheConfig caffeineConfig = cacheProperties.getCaffeine();
        RedisCacheConfig redisConfig = cacheProperties.getRedis();
        return new CompositeCache<>(List.of(
                // L1 Cache
                new CaffeineCache<>(caffeineConfig),
                // L2 Cache
                new RedisCache<>(redisConfig, cacheRedisTemplate)
        ));
    }

    /**
     * 创建{@link CacheManager}对象实例
     * <p>
     * {@link CacheManager}是Spring来管理缓存的管理器，
     * 而{@link MultilevelCacheManager}允许根据cacheName动态创建{@link MultilevelCache}缓存对象实例
     * 注册后可通过{@link Cacheable}、{@link CacheEvict}等原生注解来使用缓存
     *
     * @param cacheRedisTemplate {@link RedisTemplate}
     * @return {@link MultilevelCacheManager}
     */
    @Bean
    @Primary
    public CacheManager multilevelCacheManager(@Qualifier(REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME) RedisTemplate<String, Object> cacheRedisTemplate) {
        MultilevelCacheConfig multilevelCacheConfig = new MultilevelCacheConfig(cacheProperties.getCaffeine(), cacheProperties.getRedis());
        return new MultilevelCacheManager(multilevelCacheConfig, cacheRedisTemplate);
    }
}
