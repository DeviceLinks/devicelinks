package cn.devicelinks.transport.support.configuration;

import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.framework.common.feign.FeignClientRequestEncoder;
import cn.devicelinks.framework.common.feign.FeignClientResponseDecoder;
import cn.devicelinks.transport.support.feign.DeviceCenterSignFeignRequestInterceptor;
import cn.devicelinks.transport.support.ssl.SSLContextFactory;
import feign.Client;
import feign.Feign;
import feign.RequestInterceptor;
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
    public DeviceFeignClient deviceFeignClient() throws Exception {
        return buildFeignClient(DeviceFeignClient.class);
    }

    @Bean
    public DeviceCredentialsFeignClient deviceCredentialsFeignClient() throws Exception {
        return buildFeignClient(DeviceCredentialsFeignClient.class);
    }

    @Bean
    public RequestInterceptor deviceCenterSignFeignRequestInterceptor() {
        return new DeviceCenterSignFeignRequestInterceptor(transportProperties.getDeviceCenterApi());
    }

    private <T> T buildFeignClient(Class<T> feignClientClass) throws Exception {
        TransportProperties.DeviceCenterApi deviceCenterApi = transportProperties.getDeviceCenterApi();
        // @formatter:off
        return Feign.builder()
                .client(feignClient())
                .encoder(new FeignClientRequestEncoder())
                .decoder(new FeignClientResponseDecoder())
                .requestInterceptor(deviceCenterSignFeignRequestInterceptor())
                .target(feignClientClass, deviceCenterApi.getUri());
        // @formatter:on
    }
}
