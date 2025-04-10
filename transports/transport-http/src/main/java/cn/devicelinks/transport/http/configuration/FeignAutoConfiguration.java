package cn.devicelinks.transport.http.configuration;

import cn.devicelinks.framework.common.feign.ApiRequestSignUtils;
import cn.devicelinks.framework.common.feign.DeviceCenterDeviceFeignApi;
import cn.devicelinks.framework.common.feign.FeignConstants;
import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import cn.devicelinks.transport.http.configuration.ssl.SSLContextFactory;
import feign.Client;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

/**
 * Feign配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
public class FeignAutoConfiguration {

    private final TransportHttpProperties httpTransportProperties;

    public FeignAutoConfiguration(TransportHttpProperties httpTransportProperties) {
        this.httpTransportProperties = httpTransportProperties;
    }

    @Bean
    public Client feignClient() throws Exception {
        TransportHttpProperties.DeviceCenterApiSslConfig sslConfig = httpTransportProperties.getDeviceCenterAccess().getSsl();
        // @formatter:off
        SSLContext sslContext = SSLContextFactory.createSSLContext(
                sslConfig.getKeyStore(), sslConfig.getKeyStorePassword(),
                sslConfig.getTrustStore(), sslConfig.getTrustStorePassword()
        );
        // @formatter:on
        return new Client.Default(sslContext.getSocketFactory(), null);
    }

    @Bean
    public DeviceCenterDeviceFeignApi coreServiceDeviceApi() throws Exception {
        TransportHttpProperties.DeviceCenterApiAccessConfig apiAccessConfig = httpTransportProperties.getDeviceCenterAccess().getApi();
        // @formatter:off
        return Feign.builder()
                .client(feignClient())
                .encoder(new DeviceLinksJacksonFeignDecoder())
                .decoder(new DeviceLinksJacksonFeignEncoder())
                .requestInterceptor(signRequestInterceptor())
                .target(DeviceCenterDeviceFeignApi.class, apiAccessConfig.getUri());
        // @formatter:on
    }

    @Bean
    public RequestInterceptor signRequestInterceptor() {
        return new SignRequestInterceptor(httpTransportProperties.getDeviceCenterAccess().getApi());
    }

    /**
     * The Sign RequestInterceptor
     */
    private static class SignRequestInterceptor implements RequestInterceptor {
        private final TransportHttpProperties.DeviceCenterApiAccessConfig apiAccessConfig;

        public SignRequestInterceptor(TransportHttpProperties.DeviceCenterApiAccessConfig apiAccessConfig) {
            this.apiAccessConfig = apiAccessConfig;
        }

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            requestTemplate.header(FeignConstants.API_KEY_HEADER_NAME, apiAccessConfig.getApiKey());
            requestTemplate.header(FeignConstants.API_TIMESTAMP_HEADER_NAME, timestamp);
            requestTemplate.header(FeignConstants.API_SIGN_HEADER_NAME,
                    ApiRequestSignUtils.sign(apiAccessConfig.getApiSecret(), timestamp, requestTemplate));
        }
    }

    private static class DeviceLinksJacksonFeignDecoder extends JacksonEncoder {
        public DeviceLinksJacksonFeignDecoder() {
            super(new DeviceLinksJsonMapper());
        }
    }

    private static class DeviceLinksJacksonFeignEncoder extends JacksonDecoder {
        public DeviceLinksJacksonFeignEncoder() {
            super(new DeviceLinksJsonMapper());
        }
    }
}
