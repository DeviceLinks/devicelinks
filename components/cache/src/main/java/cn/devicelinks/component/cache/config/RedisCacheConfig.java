package cn.devicelinks.component.cache.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Redis缓存配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RedisCacheConfig extends CacheConfig {
    private String prefix = "devicelinks:cache";

    public static RedisCacheConfig useDefault() {
        return new RedisCacheConfig();
    }
}
