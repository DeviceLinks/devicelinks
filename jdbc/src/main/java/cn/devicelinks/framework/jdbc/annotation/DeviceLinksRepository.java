package cn.devicelinks.framework.jdbc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 数据接口实现类Bean注册注解
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DeviceLinksRepository {
    /**
     * Alias for {@link Component#value()}
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}
