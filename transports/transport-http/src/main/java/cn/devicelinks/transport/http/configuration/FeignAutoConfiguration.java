package cn.devicelinks.transport.http.configuration;

import cn.devicelinks.framework.common.feign.ApiRequestSignUtils;
import cn.devicelinks.framework.common.feign.DeviceCenterDeviceFeignApi;
import cn.devicelinks.framework.common.feign.FeignConstants;
import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public DeviceCenterDeviceFeignApi coreServiceDeviceApi() {
        TransportHttpProperties.DeviceCenterFeignAccessConfig coreServiceFeignConfig = httpTransportProperties.getDeviceCenterAccess();
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new DeviceLinksJacksonFeignDecoder())
                .decoder(new DeviceLinksJacksonFeignEncoder())
                .requestInterceptor(signRequestInterceptor())
                .target(DeviceCenterDeviceFeignApi.class, coreServiceFeignConfig.getUri());
    }

    @Bean
    public RequestInterceptor signRequestInterceptor() {
        return new SignRequestInterceptor(httpTransportProperties.getDeviceCenterAccess());
    }

    /**
     * The Sign RequestInterceptor
     */
    private static class SignRequestInterceptor implements RequestInterceptor {
        private final TransportHttpProperties.DeviceCenterFeignAccessConfig coreServiceFeignConfig;

        public SignRequestInterceptor(TransportHttpProperties.DeviceCenterFeignAccessConfig coreServiceFeignConfig) {
            this.coreServiceFeignConfig = coreServiceFeignConfig;
        }

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            requestTemplate.header(FeignConstants.API_KEY_HEADER_NAME, coreServiceFeignConfig.getApiKey());
            requestTemplate.header(FeignConstants.API_TIMESTAMP_HEADER_NAME, timestamp);
            requestTemplate.header(FeignConstants.API_SIGN_HEADER_NAME,
                    ApiRequestSignUtils.sign(coreServiceFeignConfig.getApiSecret(), timestamp, requestTemplate));
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
