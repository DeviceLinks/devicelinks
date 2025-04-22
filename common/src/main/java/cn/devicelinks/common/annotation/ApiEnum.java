package cn.devicelinks.common.annotation;

import java.lang.annotation.*;

/**
 * 用于接口的枚举
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiEnum {
    //...
}
