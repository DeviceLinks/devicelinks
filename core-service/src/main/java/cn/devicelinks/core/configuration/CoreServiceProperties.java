package cn.devicelinks.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static cn.devicelinks.core.configuration.CoreServiceProperties.DEVICELINKS_CORE_SERVICE_PREFIX;

/**
 * 核心服务属性配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = DEVICELINKS_CORE_SERVICE_PREFIX)
public class CoreServiceProperties {
    public static final String DEVICELINKS_CORE_SERVICE_PREFIX = "devicelinks.core";

    private List<InternalServiceApiKey> apiKeys;

    @Data
    public static class InternalServiceApiKey {
        private String apiKey;
        private String apiSecret;
        private List<InternalServiceScope> scopes;
    }

    public enum InternalServiceScope {
        /**
         * ALL Protocol Transport
         */
        Transport,
        /**
         * Console
         */
        Console
    }
}
