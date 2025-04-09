package cn.devicelinks.api.support.search;

import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;

import java.lang.annotation.*;

/**
 * 检索功能
 * <p>
 * 主要作用于分页查询接口，限制接口与具体的业务功能所关联
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchModule {
    /**
     * 检索字段所属的功能模块
     *
     * @return {@link SearchFieldModuleIdentifier}
     */
    SearchFieldModuleIdentifier module();
}
