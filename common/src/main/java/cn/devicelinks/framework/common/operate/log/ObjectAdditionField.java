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

package cn.devicelinks.framework.common.operate.log;

import cn.devicelinks.framework.common.operate.log.expression.ExpressionVariables;

import java.lang.annotation.*;

/**
 * 操作日志附加字段注解
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ObjectAdditionField {
    /**
     * 操作对象字段
     *
     * @return {@link ObjectField}
     */
    ObjectField field();

    /**
     * 前置条件
     * 支持SpEL表达式
     *
     * @return 解析后的值为 {@link Boolean} 类型，当为"true"时才会执行后续{@link #preValueLoad()}、{@link #value()}逻辑
     */
    String condition() default "true";

    /**
     * 字段值前置数据加载
     * 支持SpEL表达式
     * <p>
     * 将加载到的对象存储到{@link ExpressionVariables}变量集合中，用于后续格式化表达式使用，支持{@link #value()}使用
     *
     * @return 附加字段加载相关值SpEL表达式，解析后一般为{@link #value()}所需要的对象或者集合
     */
    String[] preValueLoad() default {};

    /**
     * 操作对象字段值
     * 支持SpEL表达式方式
     *
     * @return 操作字段获取值的SpEL表达式，解析后可以提取到字段的值
     */
    String value();
}
