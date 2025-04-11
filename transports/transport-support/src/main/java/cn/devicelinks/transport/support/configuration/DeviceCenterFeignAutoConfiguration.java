package cn.devicelinks.transport.support.configuration;

import cn.devicelinks.framework.common.feign.ApiRequestSignUtils;
import cn.devicelinks.framework.common.feign.DeviceCenterDeviceFeignApi;
import cn.devicelinks.framework.common.feign.FeignConstants;
import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import cn.devicelinks.transport.support.ssl.SSLContextFactory;
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
 * 与设备中心通信的Feign配置
 *
 * @author 恒宇少年
 * @see TransportProperties.DeviceCenterApi
 * @since 1.0
 */
@Configuration
public class DeviceCenterFeignAutoConfiguration {

    private final TransportProperties transportProperties;

    public DeviceCenterFeignAutoConfiguration(TransportProperties transportProperties) {
        this.transportProperties = transportProperties;
    }

    @Bean
    public Client feignClient() throws Exception {
        TransportProperties.DeviceCenterApi.Ssl ssl = transportProperties.getDeviceCenterApi().getSsl();
        // @formatter:off
        SSLContext sslContext = SSLContextFactory.createSSLContext(
                ssl.getKeyStore(), ssl.getKeyStorePassword(),
                ssl.getTrustStore(), ssl.getTrustStorePassword()
        );
        // @formatter:on
        return new Client.Default(sslContext.getSocketFactory(), null);
    }

    @Bean
    public DeviceCenterDeviceFeignApi coreServiceDeviceApi() throws Exception {
        TransportProperties.DeviceCenterApi deviceCenterApi = transportProperties.getDeviceCenterApi();
        // @formatter:off
        return Feign.builder()
                .client(feignClient())
                .encoder(new DeviceLinksJacksonFeignDecoder())
                .decoder(new DeviceLinksJacksonFeignEncoder())
                .requestInterceptor(signRequestInterceptor())
                .target(DeviceCenterDeviceFeignApi.class, deviceCenterApi.getUri());
        // @formatter:on
    }

    @Bean
    public RequestInterceptor signRequestInterceptor() {
        return new SignRequestInterceptor(transportProperties.getDeviceCenterApi());
    }

    /**
     * The Sign RequestInterceptor
     */
    private static class SignRequestInterceptor implements RequestInterceptor {
        private final TransportProperties.DeviceCenterApi deviceCenterApi;

        public SignRequestInterceptor(TransportProperties.DeviceCenterApi deviceCenterApi) {
            this.deviceCenterApi = deviceCenterApi;
        }

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            requestTemplate.header(FeignConstants.API_KEY_HEADER_NAME, deviceCenterApi.getApiKey());
            requestTemplate.header(FeignConstants.API_TIMESTAMP_HEADER_NAME, timestamp);
            requestTemplate.header(FeignConstants.API_SIGN_HEADER_NAME,
                    ApiRequestSignUtils.sign(deviceCenterApi.getApiSecret(), timestamp, requestTemplate));
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
