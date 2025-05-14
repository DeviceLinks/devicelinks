package cn.devicelinks.component.cache.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.Duration;

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

    public Duration toDuration() {
        return switch (this.getTtlTimeUnit()) {
            case MILLISECONDS -> Duration.ofMillis(this.getTtl());
            case SECONDS -> Duration.ofSeconds(this.getTtl());
            case MINUTES -> Duration.ofMinutes(this.getTtl());
            case HOURS -> Duration.ofHours(this.getTtl());
            case DAYS -> Duration.ofDays(this.getTtl());
            default -> Duration.ofHours(this.getTtl());
        };
    }
}
