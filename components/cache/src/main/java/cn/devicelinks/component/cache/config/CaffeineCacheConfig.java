package cn.devicelinks.component.cache.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Caffeine缓存配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CaffeineCacheConfig extends CacheConfig {

    public static CaffeineCacheConfig useDefault() {
        return new CaffeineCacheConfig();
    }
}
