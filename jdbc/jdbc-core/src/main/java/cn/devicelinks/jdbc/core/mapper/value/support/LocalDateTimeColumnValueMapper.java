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

package cn.devicelinks.jdbc.core.mapper.value.support;

import cn.devicelinks.jdbc.core.mapper.value.ColumnValueMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 将{@link java.sql.Types#TIMESTAMP}类型列值转换为{@link LocalDateTime}的映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LocalDateTimeColumnValueMapper implements ColumnValueMapper<LocalDateTime, Timestamp> {
    @Override
    public Timestamp toColumn(LocalDateTime originalValue, String columnName) {
        return originalValue != null ? Timestamp.valueOf(originalValue) : null;
    }

    @Override
    public LocalDateTime fromColumn(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
