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

    private DeviceCenterConfig deviceCenterAccess;

    @Data
    public static class DeviceCenterConfig {
        private DeviceCenterApiAccessConfig api;
        private DeviceCenterApiSslConfig ssl;
    }

    @Data
    public static class DeviceCenterApiAccessConfig {
        private String uri;
        private String apiKey;
        private String apiSecret;
    }

    @Data
    public static class DeviceCenterApiSslConfig {
        private String keyStore;
        private String keyStorePassword;
        private String trustStore;
        private String trustStorePassword;
    }
}
