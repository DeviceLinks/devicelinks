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

package cn.devicelinks.framework.jdbc;

import cn.devicelinks.framework.common.*;
import cn.devicelinks.framework.common.utils.JacksonUtils;
import cn.devicelinks.framework.jdbc.core.mapper.value.BasicColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.mapper.value.ColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.mapper.value.EnumColumnValueMapper;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 列值映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ColumnValueMappers extends BasicColumnValueMapper {
    EnumColumnValueMapper USER_IDENTITY = new EnumColumnValueMapper(UserIdentity.class);
    EnumColumnValueMapper GROUP_TYPE = new EnumColumnValueMapper(GroupType.class);
    EnumColumnValueMapper PLATFORM_TYPE = new EnumColumnValueMapper(PlatformType.class);
    EnumColumnValueMapper OPERATE_ACTION = new EnumColumnValueMapper(OperateAction.class);
    EnumColumnValueMapper OPERATE_OBJECT_TYPE = new EnumColumnValueMapper(OperateObjectType.class);
    EnumColumnValueMapper DEVICE_STATUS = new EnumColumnValueMapper(DeviceStatus.class);
    EnumColumnValueMapper DATA_FORMAT = new EnumColumnValueMapper(DataFormat.class);
    EnumColumnValueMapper DEVICE_AUTHENTICATION_METHOD = new EnumColumnValueMapper(DeviceAuthenticationMethod.class);
    EnumColumnValueMapper DEVICE_NETWORKING_AWAY = new EnumColumnValueMapper(DeviceNetworkingAway.class);
    EnumColumnValueMapper PRODUCT_STATUS = new EnumColumnValueMapper(ProductStatus.class);
    EnumColumnValueMapper SESSION_STATUS = new EnumColumnValueMapper(SessionStatus.class);


    // ------------------------------------Customize Mappers---------------------------------------//

    ColumnValueMapper<Map<String, Object>, String> JSON_MAP = new ColumnValueMapper<>() {
        @Override
        public String toColumn(Map<String, Object> originalValue, String columnName) {
            return !ObjectUtils.isEmpty(originalValue) ? JacksonUtils.toJsonString(originalValue) : null;
        }

        @Override
        public Map<String, Object> fromColumn(ResultSet rs, String columnName) throws SQLException {
            String columnValue = rs.getString(columnName);
            return !ObjectUtils.isEmpty(columnValue) ? JacksonUtils.parseMap(columnValue, String.class, Object.class) : null;
        }
    };

    ColumnValueMapper<List<String>, String> STRING_JOINER = new ColumnValueMapper<>() {
        @Override
        public String toColumn(List<String> originalValue, String columnName) {
            StringJoiner joiner = new StringJoiner(Constants.SEPARATOR);
            originalValue.forEach(joiner::add);
            return joiner.toString();
        }

        @Override
        public List<String> fromColumn(ResultSet rs, String columnName) throws SQLException {
            String columnValue = rs.getString(columnName);
            if (!ObjectUtils.isEmpty(columnValue)) {
                String[] StringArray = columnValue.split(Constants.SEPARATOR);
                return Arrays.stream(StringArray).collect(Collectors.toList());
            }
            return null;
        }
    };
}
