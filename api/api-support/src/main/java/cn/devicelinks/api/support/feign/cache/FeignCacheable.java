package cn.devicelinks.api.support.feign.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用Feign Client接口缓存注解
 * <p>
 * 标注该注解的FeignClient接口方法才会支持缓存
 * 缓存默认300秒过期，过期后会再次调用目标方法获取返回值后进行缓存
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FeignCacheable {
    /**
     * 缓存过期时长
     *
     * @return 过期时长，单位：秒
     */
    long ttlSeconds() default 300;
}
