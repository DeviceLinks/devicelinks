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

package cn.devicelinks.framework.jdbc.core.mapper.value;

import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 枚举集合类型列值映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class EnumCollectColumnValueMapper implements ColumnValueMapper<Collection<Enum>, String> {
    private static final String DELIMITER = ",";
    private final Class<? extends Enum> enumType;

    public EnumCollectColumnValueMapper(Class<? extends Enum> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String toColumn(Collection<Enum> originalValue, String columnName) {
        return !ObjectUtils.isEmpty(originalValue) ? originalValue.stream().map(Enum::toString).collect(Collectors.joining(DELIMITER)) : null;
    }

    @Override
    public Collection<Enum> fromColumn(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        if (!ObjectUtils.isEmpty(columnValue)) {
            return Arrays.stream(columnValue.split(DELIMITER)).map(e -> Enum.valueOf(this.enumType, e)).collect(Collectors.toSet());
        }
        return null;
    }
}
