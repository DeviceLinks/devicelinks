/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.jdbc.core.sql.operator;

import lombok.Getter;

/**
 * SQL查询运算符
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum SqlQueryOperator {
    /**
     * 等于
     */
    EqualTo("="),
    /**
     * 大于
     */
    GreaterThan(">"),
    /**
     * 小于
     */
    LessThan("<"),
    /**
     * 大于等于
     */
    GreaterThanOrEqualTo(">="),
    /**
     * 小于等于
     */
    LessThanOrEqualTo("<="),
    /**
     * 不等于
     */
    NotEqualTo("<>"),
    /**
     * 包含
     */
    In("in"),
    /**
     * 不包含
     */
    NotIn("not in"),
    /**
     * 模糊匹配
     */
    Like("like"),
    /**
     * 开头匹配
     */
    Prefix("like"),
    /**
     * 结尾匹配
     */
    Suffix("like");

    SqlQueryOperator(String value) {
        this.value = value;
    }

    private final String value;

}
