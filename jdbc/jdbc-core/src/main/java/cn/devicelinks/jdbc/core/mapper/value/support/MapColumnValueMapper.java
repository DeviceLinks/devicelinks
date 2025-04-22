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

import cn.devicelinks.component.jackson.DeviceLinksJsonMapper;
import cn.devicelinks.jdbc.core.mapper.value.ColumnValueMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * {@link Map}集合列值类型映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class MapColumnValueMapper implements ColumnValueMapper<Map<String, Object>, String> {
    private final DeviceLinksJsonMapper objectMapper = new DeviceLinksJsonMapper();

    @Override
    public String toColumn(Map<String, Object> originalValue, String columnName) {
        return !ObjectUtils.isEmpty(originalValue) ? this.asString(originalValue) : null;
    }

    @Override
    public Map<String, Object> fromColumn(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return !ObjectUtils.isEmpty(columnValue) ? this.parseMap(columnValue) : null;
    }

    private String asString(Map<String, Object> originalValue) {
        try {
            return this.objectMapper.writeValueAsString(originalValue);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
