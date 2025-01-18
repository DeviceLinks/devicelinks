package cn.devicelinks.framework.common.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举验证器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {
    private static final String DEFAULT_FIELD = "name";
    private Class<?> clazz;
    private String field;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.clazz = constraintAnnotation.target();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }

        if (!clazz.isEnum()) {
            return false;
        }

        Object[] enumOptions = clazz.getEnumConstants();
        if (ObjectUtils.isEmpty(enumOptions)) {
            return false;
        }

        // @formatter:off
        Optional<Object> matchOption = Arrays
                .stream(enumOptions)
                .filter(option -> {
                    if (DEFAULT_FIELD.equals(this.field)) {
                        return option.toString().equals(value.toString());
                    } else {
                        try {
                            Field otherField = option.getClass().getDeclaredField(this.field);
                            otherField.setAccessible(true);
                            return otherField.get(option).toString().equals(value.toString());
                        } catch (Exception e) {
                            return false;
                        }
                    }
                })
                .findFirst();
        // @formatter:on
        return matchOption.isPresent();
    }
}
