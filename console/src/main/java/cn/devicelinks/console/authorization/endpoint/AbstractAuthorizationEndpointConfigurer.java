package cn.devicelinks.console.authorization.endpoint;

import org.springframework.security.config.annotation.ObjectPostProcessor;

/**
 * 端点认证配置抽象实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class AbstractAuthorizationEndpointConfigurer implements AuthorizationEndpointConfigurer {
    private final ObjectPostProcessor<Object> objectPostProcessor;

    protected AbstractAuthorizationEndpointConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        this.objectPostProcessor = objectPostProcessor;
    }

    protected final <T> T postProcess(T object) {
        return (T) this.objectPostProcessor.postProcess(object);
    }

    protected final ObjectPostProcessor<Object> getObjectPostProcessor() {
        return this.objectPostProcessor;
    }
}
