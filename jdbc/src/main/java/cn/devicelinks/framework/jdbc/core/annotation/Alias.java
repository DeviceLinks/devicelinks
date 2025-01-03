package cn.devicelinks.framework.jdbc.core.annotation;

import java.lang.annotation.*;

/**
 * 别名配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {
    /**
     * 数据列别名
     *
     * @return 别名
     */
    String value();
}
