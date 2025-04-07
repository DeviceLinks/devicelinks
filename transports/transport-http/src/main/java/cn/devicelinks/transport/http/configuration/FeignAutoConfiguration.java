package cn.devicelinks.transport.http.configuration;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.feign.DeviceCenterDeviceFeignApi;
import cn.devicelinks.framework.common.feign.FeignConstants;
import cn.devicelinks.framework.common.utils.HmacSignature;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

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
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
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
                    this.sign(coreServiceFeignConfig.getApiSecret(), timestamp, requestTemplate));
        }

        private String sign(String secret, String timestamp, RequestTemplate requestTemplate) {
            String queryString = this.getQueryString(requestTemplate);
            String bodyString = this.getBodyString(requestTemplate);
            // raw string
            // @formatter:off
            String raw = (!ObjectUtils.isEmpty(queryString) ? queryString : Constants.EMPTY_STRING) +
                    (!ObjectUtils.isEmpty(bodyString) ? bodyString : Constants.EMPTY_STRING) +
                    timestamp;
            // @formatter:on
            // generate hex sign
            return HmacSignature.hmacSha256(secret).toHex(raw);
        }

        private String getQueryString(RequestTemplate template) {
            return template.queries().entrySet().stream()
                    .flatMap(e -> e.getValue().stream().map(v -> e.getKey() + "=" + v))
                    .collect(Collectors.joining("&"));
        }

        private String getBodyString(RequestTemplate template) {
            byte[] body = template.body();
            return body != null ? new String(body, StandardCharsets.UTF_8) : "";
        }
    }
}
