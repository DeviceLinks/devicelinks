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

package cn.devicelinks.component.web.search;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 检索字段值类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum SearchFieldValueType {
    /**
     * 字符串
     */
    STRING("字符串"),
    /**
     * 整数
     */
    INTEGER("整数"),
    /**
     * 浮点数
     */
    FLOAT("浮点型"),
    /**
     * 布尔
     */
    BOOLEAN("布尔型"),
    /**
     * 日期
     */
    DATE("日期"),
    /**
     * 日期时间
     */
    DATE_TIME("日期时间"),
    /**
     * 时间
     */
    TIME("时间"),
    /**
     * 枚举
     */
    ENUM("枚举")
    ;
    private final String description;

    SearchFieldValueType(String description) {
        this.description = description;
    }
}