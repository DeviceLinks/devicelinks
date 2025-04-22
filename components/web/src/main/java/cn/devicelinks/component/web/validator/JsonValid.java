package cn.devicelinks.component.web.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * 验证字符串参数是否为Json格式
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Constraint(validatedBy = {JsonValidator.class})
@Target({ElementType.FIELD, TYPE_USE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonValid {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
