package cn.devicelinks.api.device.center.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 设备中心Api配置属性
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = DeviceCenterApiProperties.DEVICE_CENTER_API_PREFIX)
public class DeviceCenterApiProperties {

    public static final String DEVICE_CENTER_API_PREFIX = "devicelinks.center.api";

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
