package cn.devicelinks.component.openfeign.configuration;

import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 设备中心Api配置属性
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = OpenFeignProperties.OPENFEIGN_PREFIX)
public class OpenFeignProperties {

    public static final String OPENFEIGN_PREFIX = "devicelinks.openfeign";
    /**
     * Key => Target Service Name {@link OpenFeignClient#name()}
     * Value => {@link FeignClientTarget}
     */
    private Map<String, FeignClientTarget> targets;

    @Data
    public static class FeignClientTarget {
        private String uri;
        private String apiKey;
        private String apiSecret;
        private Ssl ssl;
    }

    @Data
    public static class Ssl {
        private String keyStore;
        private String keyStorePassword;
        private String trustStore;
        private String trustStorePassword;
    }
}
