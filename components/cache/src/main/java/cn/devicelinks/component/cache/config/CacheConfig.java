package cn.devicelinks.component.cache.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class CacheConfig {
    public static final long DEFAULT_TTL = 60L;
    public static final TimeUnit DEFAULT_TTL_TIMEUNIT = TimeUnit.SECONDS;

    private long ttl = DEFAULT_TTL;

    private TimeUnit ttlTimeUnit = DEFAULT_TTL_TIMEUNIT;
}
