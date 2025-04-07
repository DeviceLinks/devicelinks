package cn.devicelinks.transport.http.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP协议传输服务属性配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = TransportHttpProperties.DEVICELINKS_TRANSPORT_HTTP_PREFIX)
@Data
public class TransportHttpProperties {
    public static final String DEVICELINKS_TRANSPORT_HTTP_PREFIX = "devicelinks.transport.http";

    private CoreServiceFeignConfig coreServiceAccess;

    @Data
    public static class CoreServiceFeignConfig {
        private String uri;
        private String apiKey;
        private String apiSecret;
    }
}
