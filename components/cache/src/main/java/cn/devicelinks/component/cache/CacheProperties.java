package cn.devicelinks.component.cache;

import cn.devicelinks.component.cache.config.CaffeineCacheConfig;
import cn.devicelinks.component.cache.config.RedisCacheConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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

    @NestedConfigurationProperty
    private CaffeineCacheConfig caffeine = new CaffeineCacheConfig();

    @NestedConfigurationProperty
    private RedisCacheConfig redis = new RedisCacheConfig();
}
