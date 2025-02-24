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

package cn.devicelinks.framework.jdbc.core.mapper.value;

import cn.devicelinks.framework.jdbc.core.mapper.value.support.*;

import java.sql.Types;

/**
 * 基础{@link ColumnValueMapper}定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface BasicColumnValueMapper {
    /**
     * {@link Types#VARCHAR}类型列值映射器
     */
    StringColumnValueMapper STRING = new StringColumnValueMapper();
    /**
     * {@link Types#VARCHAR}转换字符串Set集合列值映射器
     */
    StringSetColumnValueMapper STRING_SET = new StringSetColumnValueMapper();
    /**
     * {@link Types#BOOLEAN}类型列值映射器
     */
    BooleanColumnValueMapper BOOLEAN = new BooleanColumnValueMapper();
    /**
     * {@link Types#INTEGER}类型列值映射器
     */
    IntegerColumnValueMapper INTEGER = new IntegerColumnValueMapper();
    /**
     * 将{@link Types#TIMESTAMP}类型列值转换为{@link java.time.LocalDateTime}的映射器
     */
    LocalDateTimeColumnValueMapper LOCAL_DATE_TIME = new LocalDateTimeColumnValueMapper();
    /**
     * {@link Types#TIMESTAMP}类型列值映射器
     */
    TimestampColumnValueMapper TIMESTAMP = new TimestampColumnValueMapper();
    /**
     * 将{@link Types#DATE}类型列值转换为{@link java.time.LocalDate}的映射器
     */
    LocalDateColumnValueMapper LOCAL_DATE = new LocalDateColumnValueMapper();
    /**
     * 将{@link Types#TIME}类型列值转换为{@link java.time.LocalTime}的映射器
     */
    LocalTimeColumnValueMapper LOCAL_TIME = new LocalTimeColumnValueMapper();
    /**
     * Map集合列值类型映射器
     */
    MapColumnValueMapper MAP = new MapColumnValueMapper();
    /**
     * 字节数组列值类型映射器
     */
    ByteArrayColumnValueMapper BYTE_ARRAY = new ByteArrayColumnValueMapper();
}
