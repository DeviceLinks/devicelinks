package cn.devicelinks.transport.support.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static cn.devicelinks.transport.support.configuration.TransportProperties.DEVICELINKS_TRANSPORT_PREFIX;

/**
 * 传输服务属性
 * <p>
 * 传输服务公共配置，适用于全部协议的传输服务
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = DEVICELINKS_TRANSPORT_PREFIX)
public class TransportProperties {

    public static final String DEVICELINKS_TRANSPORT_PREFIX = "devicelinks.transport";

    private DeviceCenterApi deviceCenterApi;

    @Data
    public static class DeviceCenterApi {
        private String uri;
        private String apiKey;
        private String apiSecret;
        private Ssl ssl;

        @Data
        public static class Ssl {
            private String keyStore;
            private String keyStorePassword;
            private String trustStore;
            private String trustStorePassword;
        }
    }
}
