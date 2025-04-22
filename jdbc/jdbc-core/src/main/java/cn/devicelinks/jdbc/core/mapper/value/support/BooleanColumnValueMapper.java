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

/**
 * 获取{@link java.sql.Types#BOOLEAN}类型的列值
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class BooleanColumnValueMapper implements ColumnValueMapper<Boolean, Boolean> {
    @Override
    public Boolean toColumn(Boolean originalValue, String columnName) {
        return originalValue;
    }

    @Override
    public Boolean fromColumn(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }
}
