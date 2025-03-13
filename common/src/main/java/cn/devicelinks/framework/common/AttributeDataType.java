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

package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

@Getter
@ApiEnum
public enum AttributeDataType {
    /**
     * 整型
     */
    INTEGER("整型"),
    /**
     * 浮点型
     */
    DOUBLE("浮点型"),
    /**
     * 枚举
     */
    ENUM("枚举"),
    /**
     * 布尔
     */
    BOOLEAN("布尔"),
    /**
     * 字符串
     */
    STRING("字符串"),
    /**
     * 日期
     */
    DATE("日期"),
    /**
     * 日期时间
     */
    DATETIME("日期时间"),
    /**
     * 时间
     */
    TIME("时间"),
    /**
     * 时间戳
     */
    TIMESTAMP("时间戳"),
    /**
     * JSON
     */
    JSON("JSON"),
    /**
     * 数组
     */
    ARRAY("数组");

    private final String description;

    AttributeDataType(String description) {
        this.description = description;
    }
}
