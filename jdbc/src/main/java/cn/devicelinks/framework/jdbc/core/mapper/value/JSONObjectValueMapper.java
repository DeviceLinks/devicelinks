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

import cn.devicelinks.framework.common.utils.JacksonUtils;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将JSON字符串映射成指定类型的对象实例
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class JSONObjectValueMapper implements ColumnValueMapper<Object, String> {
    private final Class<?> objectClass;

    public JSONObjectValueMapper(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    @Override
    public String toColumn(Object originalValue, String columnName) {
        return !ObjectUtils.isEmpty(originalValue) ? JacksonUtils.objectToJson(originalValue) : null;
    }

    @Override
    public Object fromColumn(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return !ObjectUtils.isEmpty(columnValue) ? JacksonUtils.jsonToObject(columnValue, objectClass) : null;
    }
}
