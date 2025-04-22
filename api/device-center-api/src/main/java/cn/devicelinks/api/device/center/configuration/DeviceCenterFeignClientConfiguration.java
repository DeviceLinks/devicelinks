package cn.devicelinks.api.device.center.configuration;

import cn.devicelinks.api.device.center.CommonFeignClient;
import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.support.feign.FeignClientRequestEncoder;
import cn.devicelinks.api.support.feign.FeignClientResponseDecoder;
import cn.devicelinks.api.support.ssl.SSLContextFactory;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;

/**
 * 设备中心FeignClient配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
@EnableConfigurationProperties(DeviceCenterApiProperties.class)
public class DeviceCenterFeignClientConfiguration {

    private final DeviceCenterApiProperties deviceCenterApiProperties;

    public DeviceCenterFeignClientConfiguration(DeviceCenterApiProperties deviceCenterApiProperties) {
        this.deviceCenterApiProperties = deviceCenterApiProperties;
    }

    @Bean
    public Client feignClient() throws Exception {
        DeviceCenterApiProperties.Ssl ssl = deviceCenterApiProperties.getSsl();
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
    public CommonFeignClient commonFeignClient() throws Exception {
        return buildFeignClient(CommonFeignClient.class);
    }

    private <T> T buildFeignClient(Class<T> feignClientClass) throws Exception {
        // @formatter:off
        return Feign.builder()
                .client(feignClient())
                .encoder(new FeignClientRequestEncoder())
                .decoder(new FeignClientResponseDecoder())
                .logger(new Slf4jLogger(feignClientClass))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(new DeviceCenterFeignClientSignRequestInterceptor(deviceCenterApiProperties))
                .target(feignClientClass, deviceCenterApiProperties.getUri());
        // @formatter:on
    }
}
