package cn.devicelinks.api.support.feign.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import feign.InvocationHandlerFactory;
import feign.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The {@link InvocationHandlerFactory} Cache Implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CachingFeignInvocationHandlerFactory implements InvocationHandlerFactory {
    private final InvocationHandlerFactory delegate;
    private final Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(1000)
            .build();

    public CachingFeignInvocationHandlerFactory(InvocationHandlerFactory delegate) {
        this.delegate = delegate;
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
