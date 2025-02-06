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
public enum SearchFieldOperator {
    /**
     * 等于
     */
    EQUAL("等于", "EQUAL"),
    /**
     * 不等于
     */
    NOT_EQUAL("不等于", "NOT_EQUAL"),
    /**
     * 大于
     */
    GREATER_THAN("大于", "GREATER_THAN"),
    /**
     * 大于等于
     */
    GREATER_THAN_OR_EQUAL("大于等于", "GREATER_THAN_OR_EQUAL"),
    /**
     * 小于
     */
    LESS_THAN("小于", "LESS_THAN"),
    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL("小于等于", "LESS_THAN_OR_EQUAL"),
    /**
     * 包含
     */
    IN("包含", "IN"),
    /**
     * 不包含
     */
    NOT_IN("不包含", "NOT_IN"),
    /**
     * 模糊匹配
     */
    LIKE("模糊匹配", "LIKE"),
    /**
     * 不模糊匹配
     */
    NOT_LIKE("不模糊匹配", "NOT_LIKE");
    private final String label;
    private final String value;

    SearchFieldOperator(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
