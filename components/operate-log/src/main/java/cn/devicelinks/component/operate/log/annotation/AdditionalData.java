/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.component.operate.log.annotation;

import cn.devicelinks.component.operate.log.AdditionalDataLoadTime;

import java.lang.annotation.*;

/**
 * 附加数据
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdditionalData {
    /**
     * 附加数据的唯一Key
     * <p>
     * 如果重复配置则会覆盖数据
     *
     * @return 附加数据Key
     */
    String key();

    /**
     * 附加数据加载时机
     *
     * @return {@link AdditionalDataLoadTime}
     */
    AdditionalDataLoadTime loadTime() default AdditionalDataLoadTime.All;

    /**
     * 前置条件
     * 支持SpEL表达式
     *
     * @return 解析后的值为 {@link Boolean} 类型，当为"true"时才会执行后续{@link #data()}
     */
    String condition() default "true";

    /**
     * 附加数据查询表达式
     * 支持SpEL表达式
     *
     * @return 操作字段获取值的SpEL表达式，解析后可以提取到字段的值
     */
    String data();
}
