package cn.devicelinks.component.openfeign.configuration;

import cn.devicelinks.common.exception.DeviceLinksException;
import cn.devicelinks.component.cache.core.CaffeineCache;
import cn.devicelinks.component.openfeign.OpenFeignClientRequestEncoder;
import cn.devicelinks.component.openfeign.OpenFeignClientResponseDecoder;
import cn.devicelinks.component.openfeign.OpenFeignClientSignRequestInterceptor;
import cn.devicelinks.component.openfeign.cache.CachingFeignInvocationHandlerFactory;
import cn.devicelinks.component.openfeign.ssl.SslContextFactory;
import feign.*;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.SSLContext;
import java.util.Map;

/**
 * OpenFeignClient对象工厂类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class OpenFeignClientFactoryBean implements FactoryBean<Object>, BeanFactoryAware {
    private final Class<?> feignClientClass;
    private final String targetServiceName;
    private BeanFactory beanFactory;

    public OpenFeignClientFactoryBean(Class<?> feignClientClass, String targetServiceName) {
        this.feignClientClass = feignClientClass;
        this.targetServiceName = targetServiceName;
    }

    @Override
    public Object getObject() throws Exception {
        OpenFeignProperties.FeignClientTarget feignClientTarget = this.getFeignClientTarget();

        // Request Cache, Use CaffeineCache
        InvocationHandlerFactory invocationHandlerFactory =
                new CachingFeignInvocationHandlerFactory(new InvocationHandlerFactory.Default(),
                        new CaffeineCache<>());
        // Generate signature interceptor
        RequestInterceptor signRequestInterceptor =
                new OpenFeignClientSignRequestInterceptor(feignClientTarget.getApiKey(), feignClientTarget.getApiSecret());

        // @formatter:off
        return Feign.builder()
                .client(this.createSslClient(feignClientTarget.getSsl()))
                .invocationHandlerFactory(invocationHandlerFactory)
                .encoder(new OpenFeignClientRequestEncoder())
                .decoder(new OpenFeignClientResponseDecoder())
                .logger(new Slf4jLogger(feignClientClass))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(signRequestInterceptor)
                .target(feignClientClass, feignClientTarget.getUri());
        // @formatter:on
    }

    @Override
    public Class<?> getObjectType() {
        return feignClientClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private OpenFeignProperties.FeignClientTarget getFeignClientTarget() {
        ObjectProvider<OpenFeignProperties> openFeignPropertiesObjectProvider = beanFactory.getBeanProvider(OpenFeignProperties.class);
        OpenFeignProperties openFeignProperties = openFeignPropertiesObjectProvider.getIfAvailable();
        if (openFeignProperties == null || ObjectUtils.isEmpty(openFeignProperties.getTargets())) {
            throw new DeviceLinksException("Unable to load feign targets configured in OpenFeignProperties.");
        }
        Map<String, OpenFeignProperties.FeignClientTarget> targets = openFeignProperties.getTargets();
        return targets.get(this.targetServiceName);
    }

    private Client createSslClient(OpenFeignProperties.Ssl ssl) throws Exception {
        // @formatter:off
        SSLContext sslContext = SslContextFactory.createSSLContext(
                ssl.getKeyStore(), ssl.getKeyStorePassword(),
                ssl.getTrustStore(), ssl.getTrustStorePassword()
        );
        // @formatter:on
        return new Client.Default(sslContext.getSocketFactory(), null);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
