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

package cn.devicelinks.framework.jdbc.core.utils;

import cn.devicelinks.framework.common.utils.ObjectClassUtils;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.annotation.IdGenerationStrategy;
import cn.devicelinks.framework.jdbc.core.definition.Table;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.core.sql.Condition;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.core.sql.ConditionValue;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlParameterValue;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * {@link SqlParameterValue}操作工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class SqlParameterValueUtils {
    /**
     * 根据查询条件{@link Condition}转换{@link SqlParameterValue}
     *
     * @param table      获取查询条件的表{@link Table}
     * @param conditions {@link Condition} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithCondition(Table table, List<Condition> conditions) {
        // @formatter:off
        return conditions
                .stream()
                .flatMap(condition -> {
                    Column column = table.getColumn(condition.getColumnName());
                    if (!table.containsColumn(condition.getColumnName())) {
                        log.error("未知的列, 在表{}中并未发现查询条件所使用的列{}.", ((TableImpl) table).getTableName(), condition.getColumnName());
                    }
                    // 查询运算符为In或者NotIn并且条件值为List时, 将List中的每个元素都对应实例化SqlParameterValue
                    if (condition.getParameterValue() instanceof Collection<?> parameterValueList &&
                            (SqlQueryOperator.In == condition.getOperator() || SqlQueryOperator.NotIn == condition.getOperator())) {
                        return parameterValueList.stream()
                                .map(parameterValue -> {
                                    Object convertedValue = column.toColumnValue(parameterValue);
                                    return new SqlParameterValue(column.getSqlType(), convertedValue);
                                })
                                .toList()
                                .stream();
                    }
                    Object convertedValue = column.toColumnValue(condition.getParameterValue());
                    return Stream.of(new SqlParameterValue(column.getSqlType(), convertedValue));
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }

    /**
     * 根据查询条件分组{@link ConditionGroup}转换{@link SqlParameterValue}
     *
     * @param table           获取查询条件的表{@link Table}
     * @param conditionGroups {@link ConditionGroup} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithConditionGroup(Table table, List<ConditionGroup> conditionGroups) {
        // @formatter:off
        List<Condition> conditions = conditionGroups
                .stream()
                .flatMap(conditionGroup -> conditionGroup.getConditions().stream())
                .toList();
        // @formatter:on
        return getWithCondition(table, conditions);
    }

    /**
     * 根据列值关系实体{@link ConditionValue}转换{@link SqlParameterValue}
     *
     * @param table           获取查询条件的表{@link Table}
     * @param conditionValues {@link ConditionValue} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithColumnValue(Table table, ConditionValue... conditionValues) {
        // @formatter:off
        return Arrays.stream(conditionValues)
                .filter(conditionValue -> table.containsColumn(conditionValue.getColumn().getName()))
                .map(conditionValue -> {
                    Column column = conditionValue.getColumn();
                    Object convertedValue = column.toColumnValue(conditionValue.getValue());
                    return new SqlParameterValue(column.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }

    /**
     * 根据表列定义{@link Column}转换{@link SqlParameterValue}
     *
     * @param columns            {@link Column} 对象列表
     * @param getMethodResultMap 列对应字段{@link java.lang.reflect.Field}的对象Get方法值集合
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithTableColumn(List<Column> columns, Map<String, Object> getMethodResultMap) {
        // @formatter:off
        return columns.stream()
                .filter(column -> {
                    if (column.isPk() && IdGenerationStrategy.AUTO_INCREMENT == column.getIdGenerationStrategy()) {
                        return false;
                    }
                    return true;
                })
                .map(tableColumn -> {
                    String getMethodName = Types.BOOLEAN == tableColumn.getSqlType()
                            ? ObjectClassUtils.getIsMethodName(tableColumn.getUpperCamelName())
                            : ObjectClassUtils.getGetMethodName(tableColumn.getUpperCamelName());
                    Object getMethodResult = getMethodResultMap.get(getMethodName);
                    Object convertedValue = tableColumn.toColumnValue(getMethodResult);
                    return new SqlParameterValue(tableColumn.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }

    /**
     * 根据列值{@link ConditionValue}转换{@link SqlParameterValue}
     *
     * @param conditionValues {@link ConditionValue} 对象列表
     * @return {@link SqlParameterValue} 对象列表
     */
    public static SqlParameterValue[] getWithColumnValue(Table table, List<ConditionValue> conditionValues) {
        // @formatter:off
        return conditionValues.stream()
                .filter(conditionValue -> table.containsColumn(conditionValue.getColumn().getName()))
                .map(conditionValue -> {
                    Column column = conditionValue.getColumn();
                    Object convertedValue = column.toColumnValue(conditionValue.getValue());
                    return new SqlParameterValue(column.getSqlType(), convertedValue);
                })
                .toArray(SqlParameterValue[]::new);
        // @formatter:on
    }
}
