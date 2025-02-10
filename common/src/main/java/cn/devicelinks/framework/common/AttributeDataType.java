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

/**
 * 属性数据类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum AttributeDataType {
    /**
     * 整型
     */
    INT32,
    /**
     * 长整型
     */
    LONG64,
    /**
     * 浮点型
     */
    FLOAT,
    /**
     * 双精度浮点型
     */
    DOUBLE,
    /**
     * 枚举
     */
    ENUM,
    /**
     * 布尔
     */
    BOOLEAN,
    /**
     * 字符串
     */
    STRING,
    /**
     * 日期
     */
    DATE,
    /**
     * 日期时间
     */
    DATETIME,
    /**
     * 时间
     */
    TIME,
    /**
     * 时间戳
     */
    TIMESTAMP,
    /**
     * 对象
     */
    OBJECT,
    /**
     * 数组
     */
    ARRAY
}
