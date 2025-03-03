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

package cn.devicelinks.framework.common.web;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 检索字段运算符定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiEnum
public enum SearchFieldOperator {
    /**
     * 等于
     */
    EqualTo("等于", "EqualTo"),
    /**
     * 不等于
     */
    NotEqualTo("不等于", "NotEqualTo"),
    /**
     * 大于
     */
    GreaterThan("大于", "GreaterThan"),
    /**
     * 大于等于
     */
    GreaterThanOrEqualTo("大于等于", "GreaterThanOrEqualTo"),
    /**
     * 小于
     */
    LessThan("小于", "LessThan"),
    /**
     * 小于等于
     */
    LessThanOrEqualTo("小于等于", "LessThanOrEqualTo"),
    /**
     * 包含
     */
    In("包含", "In"),
    /**
     * 不包含
     */
    NotIn("不包含", "NotIn"),
    /**
     * 模糊匹配
     */
    Like("模糊匹配", "Like"),
    /**
     * 不模糊匹配
     */
    NotLike("不模糊匹配", "NotLike");
    private final String description;
    private final String value;

    SearchFieldOperator(String description, String value) {
        this.description = description;
        this.value = value;
    }
}
