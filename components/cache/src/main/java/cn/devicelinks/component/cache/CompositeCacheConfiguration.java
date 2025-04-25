package cn.devicelinks.component.cache;

import cn.devicelinks.component.cache.core.Cache;
import cn.devicelinks.component.cache.core.CaffeineCache;
import cn.devicelinks.component.cache.core.CompositeCache;
import cn.devicelinks.component.cache.core.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 复合多级缓存配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
public class CompositeCacheConfiguration {

    private final CacheProperties cacheProperties;

    public CompositeCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    public Cache<String, Object> compositeCache(RedisTemplate<String, Object> cacheRedisTemplate) {
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
