package cn.devicelinks.component.openfeign.annotation;

import cn.devicelinks.component.openfeign.configuration.OpenFeignProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenFeignClient {
    /**
     * 获取目标服务的名称
     * <p>
     * 如果是同一个服务的OpenFeignClient，可以配置相同的目标服务名称，
     * 会从{@link OpenFeignProperties}中读取该服务对应的参数用于发起请求
     *
     * @return 目标服务名称
     */
    String name();
}

