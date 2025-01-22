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

import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举类型列值映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class EnumColumnValueMapper implements ColumnValueMapper<Enum, String> {
    private final Class<? extends Enum> enumType;

    public EnumColumnValueMapper(Class<? extends Enum> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String toColumn(Enum originalValue, String columnName) {
        return originalValue != null ? originalValue.toString() : null;
    }

    @Override
    public Enum fromColumn(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return !ObjectUtils.isEmpty(columnValue) ? Enum.valueOf(this.enumType, columnValue) : null;
    }
}
