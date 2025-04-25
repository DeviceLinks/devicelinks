package cn.devicelinks.api.device.center.configuration;

import cn.devicelinks.api.device.center.CommonFeignClient;
import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.support.feign.FeignClientRequestEncoder;
import cn.devicelinks.api.support.feign.FeignClientResponseDecoder;
import cn.devicelinks.api.support.feign.cache.CachingFeignInvocationHandlerFactory;
import cn.devicelinks.api.support.ssl.SSLContextFactory;
import cn.devicelinks.component.cache.CacheType;
import cn.devicelinks.component.cache.annotation.EnableCache;
import cn.devicelinks.component.cache.core.Cache;
import feign.Client;
import feign.Feign;
import feign.InvocationHandlerFactory;
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
@EnableCache(cacheType = CacheType.Composite)
public class DeviceCenterFeignClientConfiguration {

    private final DeviceCenterApiProperties deviceCenterApiProperties;

    private final Cache<String, Object> cache;

    public DeviceCenterFeignClientConfiguration(DeviceCenterApiProperties deviceCenterApiProperties, Cache<String, Object> cache) {
        this.deviceCenterApiProperties = deviceCenterApiProperties;
        this.cache = cache;
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
                .invocationHandlerFactory(new CachingFeignInvocationHandlerFactory(new InvocationHandlerFactory.Default(), this.cache))
                .encoder(new FeignClientRequestEncoder())
                .decoder(new FeignClientResponseDecoder())
                .logger(new Slf4jLogger(feignClientClass))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(new DeviceCenterFeignClientSignRequestInterceptor(deviceCenterApiProperties))
                .target(feignClientClass, deviceCenterApiProperties.getUri());
        // @formatter:on
    }
}
