package cn.devicelinks.component.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static cn.devicelinks.component.cache.CacheProperties.CACHE_CONFIG_PREFIX;

/**
 * 缓存属性配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ConfigurationProperties(prefix = CACHE_CONFIG_PREFIX)
@Data
public class CacheProperties {

    public static final String CACHE_CONFIG_PREFIX = "devicelinks.cache";

    private CacheCaffeineConfig caffeine = new CacheCaffeineConfig();

    private CacheRedisConfig redis = new CacheRedisConfig();

    @Data
    public static class CacheCaffeineConfig {
        private long ttlSeconds = 60L;
        private long maximumSize = 10_1000L;
    }

    @Data
    public static class CacheRedisConfig {
        private String prefix = "devicelinks:cache";
        private long ttlSeconds = 60L;
    }
}
