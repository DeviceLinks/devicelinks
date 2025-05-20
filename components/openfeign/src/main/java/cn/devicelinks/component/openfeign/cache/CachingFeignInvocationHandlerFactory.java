package cn.devicelinks.component.openfeign.cache;

import cn.devicelinks.component.cache.core.Cache;
import feign.InvocationHandlerFactory;
import feign.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * The {@link InvocationHandlerFactory} Cache Implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CachingFeignInvocationHandlerFactory implements InvocationHandlerFactory {
    private final InvocationHandlerFactory delegate;
    private final Cache<String, Object> cache;

    public CachingFeignInvocationHandlerFactory(InvocationHandlerFactory delegate, Cache<String, Object> cache) {
        this.delegate = delegate;
        this.cache = cache;
    }

    @Override
    public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
        InvocationHandler handler = delegate.create(target, dispatch);

        return (proxy, method, args) -> {
            FeignCacheable cacheable = method.getAnnotation(FeignCacheable.class);
            if (cacheable != null) {
                String key = FeignCacheKeyBuilder.build(method, args);
                return cache.get(key, () -> {
                    try {
                        return handler.invoke(proxy, method, args);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                return handler.invoke(proxy, method, args);
            }
        };
    }
}
