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

/**
 * 检索字段值类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum SearchFieldValueType {
    /**
     * 字符串
     */
    STRING,
    /**
     * 整数
     */
    INTEGER,
    /**
     * 浮点数
     */
    FLOAT,
    /**
     * 布尔
     */
    BOOLEAN,
    /**
     * 日期
     */
    DATE,
    /**
     * 日期时间
     */
    DATE_TIME,
    /**
     * 时间
     */
    TIME,
    /**
     * 枚举
     */
    ENUM
}