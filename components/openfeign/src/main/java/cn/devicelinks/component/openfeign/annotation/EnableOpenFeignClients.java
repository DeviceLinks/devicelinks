package cn.devicelinks.component.openfeign.annotation;

import cn.devicelinks.component.openfeign.configuration.OpenFeignClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用Openfeign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(OpenFeignClientsRegistrar.class)
public @interface EnableOpenFeignClients {
    /**
     * 启用指定的客户端列表
     * <p>
     * 如果不指定则自动扫描并启用全部的{@link OpenFeignClient}标注的接口
     *
     * @return {@link OpenFeignClient}标注的接口列表
     */
    Class<?>[] clients() default {};
}
