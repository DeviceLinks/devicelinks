package cn.devicelinks.center.configuration;

import cn.devicelinks.common.secret.AesSecretKeySet;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static cn.devicelinks.center.configuration.DeviceCenterProperties.DEVICELINKS_CORE_SERVICE_PREFIX;

/**
 * 设备中心属性配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = DEVICELINKS_CORE_SERVICE_PREFIX)
@Slf4j
public class DeviceCenterProperties {
    public static final String DEVICELINKS_CORE_SERVICE_PREFIX = "devicelinks.center";

    private List<InternalServiceApiKey> apiKeys;

    /**
     * DeviceSecret存储时AES加密算法的Keys
     */
    @NestedConfigurationProperty
    private AesSecretKeySet deviceSecretKeySet;

    private TokenSetting tokenSetting;

    @Data
    public static class TokenSetting {
        private long validitySeconds = 7200;
        private int issuedDynamicTokenLength = 64;

        public void setIssuedDynamicTokenLength(int issuedDynamicTokenLength) {
            if (issuedDynamicTokenLength < 64) {
                log.warn("The length of the issued dynamic token is not recommended to be less than 64 bits.");
            }
            this.issuedDynamicTokenLength = issuedDynamicTokenLength;
        }
    }

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
