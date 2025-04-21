package cn.devicelinks.framework.common.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

import static cn.devicelinks.framework.common.annotation.DeviceLinksBeanScan.DEVICELINKS_BASE_SCAN_PACKAGE;

/**
 * Bean扫描注解
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan(basePackages = DEVICELINKS_BASE_SCAN_PACKAGE)
public @interface DeviceLinksBeanScan {

    /**
     * Scan the package name of the devicelinks spring bean
     */
    String DEVICELINKS_BASE_SCAN_PACKAGE = "cn.devicelinks";

}
