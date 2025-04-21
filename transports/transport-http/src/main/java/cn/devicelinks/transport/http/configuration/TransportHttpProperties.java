package cn.devicelinks.transport.http.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TransportHttpProperties {
    public static final String DEVICELINKS_TRANSPORT_HTTP_PREFIX = "devicelinks.transport.http";
}
