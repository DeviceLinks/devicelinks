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

package cn.devicelinks.framework.jdbc.core.mapper;

import cn.devicelinks.framework.common.utils.ObjectClassUtils;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.jdbc.core.annotation.Alias;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.EntityStructure;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据行与实体封装映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ResultRowMapper<T> implements RowMapper<T> {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ResultRowMapper.class);
    @Getter
    private final List<Map<String, Object>> allRowValueMap;
    private final EntityStructure structure;

    public ResultRowMapper(EntityStructure structure) {
        this.structure = structure;
        this.allRowValueMap = new ArrayList<>();
    }

    /**
     * 映射封装返回对象
     *
     * @param rs     {@link ResultSet}
     * @param rowNum 当前行的索引
     * @return 映射封装后的对象，类型为{@link EntityStructure#getEntityClass()}
     */
    @Override
    public T mapRow(@Nullable ResultSet rs, int rowNum) throws SQLException {
        try {
            // Instance Result Object
            Class<?> mapEntityClass = this.structure.getEntityClass();
            Object mapEntityObject = mapEntityClass.getConstructor().newInstance();
            Map<String, Object> rowValueMap = new HashMap<>();
            Map<String, Object> setMethodValueMap = new HashMap<>();
            Field[] fields = ObjectClassUtils.getClassFields(mapEntityClass);
            Arrays.stream(fields)
                    .forEach(field -> {
                        String alias = field.isAnnotationPresent(Alias.class) ? field.getAnnotation(Alias.class).value() : null;
                        String columnName = StringUtils.lowerCamelToLowerUnder(!ObjectUtils.isEmpty(alias) ? alias : field.getName());
                        Column column = this.structure.getColumn(columnName);
                        if (column == null) {
                            logger.warn("ResultType: [{}], Column: [{}] is not defined.", mapEntityClass.getName(), columnName);
                            return;
                        }
                        // Get the converted column value
                        Object columnValue;
                        try {
                            columnValue = column.fromColumnValue(rs, columnName);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        String setMethodName = ObjectClassUtils.getSetMethodName(StringUtils.lowerCamelToUpperCamel(field.getName()));
                        setMethodValueMap.put(setMethodName, columnValue);
                        rowValueMap.put(columnName, columnValue);

                    });
            this.allRowValueMap.add(rowValueMap);
            // Invoke Result Object Set Methods
            ObjectClassUtils.invokeObjectSetMethod(mapEntityObject, setMethodValueMap);
            return (T) mapEntityObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取{@link ResultSetMetaData}返回的全部列名
     *
     * @param rs {@link ResultSet}
     * @return 列名列表
     */
    private List<String> getResultSetColumnNameList(ResultSet rs) throws SQLException {
        List<String> columnNameList = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            columnNameList.add(metaData.getColumnName(i + 1));
        }
        return columnNameList;
    }
}
