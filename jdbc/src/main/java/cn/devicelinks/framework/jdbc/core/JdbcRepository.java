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

package cn.devicelinks.framework.jdbc.core;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.exception.DeviceLinksException;
import cn.devicelinks.framework.common.utils.ObjectClassUtils;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.Table;
import cn.devicelinks.framework.jdbc.core.mapper.ResultRowMapper;
import cn.devicelinks.framework.jdbc.core.page.DefaultPageResult;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.printer.ConsoleSqlPrinter;
import cn.devicelinks.framework.jdbc.core.printer.SqlPrinter;
import cn.devicelinks.framework.jdbc.core.sql.*;
import cn.devicelinks.framework.jdbc.core.utils.SqlParameterValueUtils;
import com.alibaba.druid.sql.SQLUtils;
import org.springframework.core.ResolvableType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 数据存储库接口JDBC实现类
 *
 * @author 恒宇少年
 * @see JdbcOperations
 * @see Table
 * @see Condition
 * @see ConditionGroup
 * @see Column
 * @see ConditionValue
 * @since 1.0
 */
public class JdbcRepository<T extends Serializable, PK> implements Repository<T, PK> {
    private static final int MAP_ENTITY_GENERIC_INDEX = 0;
    protected Table table;
    protected RepositoryOperations jdbcRepositoryOperations;
    private final Class mapEntityClass;
    private final SqlPrinter sqlPrinter = new ConsoleSqlPrinter(this.getClass());

    public JdbcRepository(Table table, JdbcOperations jdbcOperations) {
        this.table = table;
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).getSuperType();
        this.mapEntityClass = resolvableType.getGeneric(MAP_ENTITY_GENERIC_INDEX).resolve();
        this.jdbcRepositoryOperations = new JdbcRepositoryOperations(jdbcOperations, this.sqlPrinter);
    }

    @Override
    public int insert(T object) {
        Assert.notNull(object, "目标新增对象不可以为null.");
        List<Column> insertableColumns = this.table.getInsertableColumns();
        Map<String, Object> methodValueMap = ObjectClassUtils.invokeObjectGetMethod(object);
        String insertSql = this.table.getInsertSql();
        SqlParameterValue[] sqlParameterValues = SqlParameterValueUtils.getWithTableColumn(insertableColumns, methodValueMap);
        return this.jdbcRepositoryOperations.insert(insertSql, Arrays.asList(sqlParameterValues));
    }

    @Override
    public int delete(PK pk) {
        Assert.notNull(pk, "主键值不可以为null.");
        // @formatter:off
        ConditionSql conditionSql = ConditionSql
                .delete(this.table)
                .condition(this.table.getPk().eq(pk))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.delete(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int delete(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // @formatter:off
        ConditionSql conditionSql = ConditionSql
                .delete(this.table)
                .conditions(cds -> cds.addAll(Arrays.asList(conditions)))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.delete(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int delete(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // @formatter:off
        ConditionSql conditionSql = ConditionSql
                .delete(this.table)
                .conditionGroups(groups -> groups.addAll(Arrays.asList(conditionGroups)))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.delete(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int delete(String filterSql, ConditionValue... filterConditionValues) {
        Assert.hasText(filterSql, "filterSql must not be empty.");
        Assert.notEmpty(filterConditionValues, "请至少传递一个FilterColumnValue.");
        // @formatter:off
        ConditionSql conditionSql = ConditionSql
                .delete(this.table)
                .filterSql(filterSql)
                .filterConditionValues(values -> values.addAll(Arrays.asList(filterConditionValues)))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.delete(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int update(T object) {
        Assert.notNull(object, "目标更新对象不可以为null.");
        Column pkColumn = this.table.getPk();
        Map<String, Object> methodValueMap = ObjectClassUtils.invokeObjectGetMethod(object);
        Object pkValue = methodValueMap.get(ObjectClassUtils.getGetMethodName(pkColumn.getUpperCamelName()));
        List<Column> updatableColumnList = this.table.getUpdatableColumns();
        SqlParameterValue[] parameterValues = SqlParameterValueUtils.getWithTableColumn(updatableColumnList, methodValueMap);
        updatableColumnList.add(this.table.getPk());
        // @formatter:off
        ConditionSql conditionSql = ConditionSql
                .update(this.table)
                .columnValues(values -> values.addAll(Arrays.asList(parameterValues)))
                .condition(pkColumn.eq(pkValue))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.update(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int update(List<ConditionValue> setConditionValueList, Condition... conditions) {
        Assert.notEmpty(setConditionValueList, "请至少传递一个列值.");
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // @formatter:off
        List<Column> setColumnList = setConditionValueList.stream()
                .map(ConditionValue::getColumn)
                .toList();
        ConditionSql conditionSql = ConditionSql
                .update(this.table)
                .columns(columns -> columns.addAll(setColumnList))
                .columnValues(values -> values.addAll(Arrays.asList(SqlParameterValueUtils.getWithColumnValue(this.table, setConditionValueList))))
                .conditions(cds -> cds.addAll(Arrays.asList(conditions)))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.update(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int update(List<ConditionValue> setConditionValueList, ConditionGroup... conditionGroups) {
        Assert.notEmpty(setConditionValueList, "请至少传递一个列值.");
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // @formatter:off
        List<Column> setColumnList = setConditionValueList.stream()
                .map(ConditionValue::getColumn)
                .toList();
        ConditionSql conditionSql = ConditionSql
                .update(this.table)
                .columns(columns -> columns.addAll(setColumnList))
                .columnValues(values -> values.addAll(Arrays.asList(SqlParameterValueUtils.getWithColumnValue(this.table, setConditionValueList))))
                .conditionGroups(groups -> groups.addAll(Arrays.asList(conditionGroups)))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.update(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public int update(List<ConditionValue> setConditionValueList, String filterSql, ConditionValue... filterConditionValues) {
        Assert.notEmpty(setConditionValueList, "请至少传递一个列值.");
        Assert.hasText(filterSql, "自定义过滤SQL不可以为空.");
        Assert.notEmpty(filterConditionValues, "请至少传递一个过滤数据的列值.");
        // @formatter:off
        List<Column> setColumnList = setConditionValueList.stream()
                .map(ConditionValue::getColumn)
                .toList();
        ConditionSql conditionSql = ConditionSql
                .update(this.table)
                .columns(columns -> columns.addAll(setColumnList))
                .columnValues(values -> values.addAll(Arrays.asList(SqlParameterValueUtils.getWithColumnValue(this.table, setConditionValueList))))
                .filterSql(filterSql)
                .filterConditionValues(values -> values.addAll(Arrays.asList(filterConditionValues)))
                .build();
        // @formatter:on
        return this.jdbcRepositoryOperations.update(conditionSql.getSql(), conditionSql.getSqlParameterValues());
    }

    @Override
    public T selectOne(PK pk) {
        Assert.notNull(pk, "主键值不可以为null.");
        return this.selectOne(this.table.getPk().eq(pk));
    }

    @Override
    public T selectOne(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // @formatter:off
        List<T> resultList = this.select(FusionCondition
                .withConditions(conditions)
                .limit(LimitCondition.withLimit(Constants.ONE))
                .build());
        // @formatter:on
        return !ObjectUtils.isEmpty(resultList) ? resultList.getFirst() : null;
    }

    @Override
    public T selectOne(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // @formatter:off
        List<T> resultList = this.select(FusionCondition
                .withConditionGroups(conditionGroups)
                .limit(LimitCondition.withLimit(Constants.ONE))
                .build());
        // @formatter:on
        return !ObjectUtils.isEmpty(resultList) ? resultList.getFirst() : null;
    }

    @Override
    public List<T> select() {
        return this.select(FusionCondition.empty());
    }

    @Override
    public List<T> select(Condition... conditions) {
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // @formatter:off
        return this.select(FusionCondition
                .withConditions(conditions)
                .build());
        // @formatter:on
    }

    @Override
    public List<T> select(SortCondition sort, Condition... conditions) {
        Assert.notNull(sort, "SortCondition不可以为null.");
        Assert.notEmpty(conditions, "请至少传递一个Condition.");
        // @formatter:off
        return this.select(FusionCondition
                .withConditions(conditions)
                .sort(sort)
                .build());
        // @formatter:on
    }

    @Override
    public List<T> select(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // @formatter:off
        return this.select(FusionCondition
                .withConditionGroups(conditionGroups)
                .build());
        // @formatter:on
    }

    @Override
    public List<T> select(SortCondition sort, ConditionGroup... conditionGroups) {
        Assert.notNull(sort, "SortCondition不可以为null.");
        Assert.notEmpty(conditionGroups, "请至少传递一个ConditionGroup.");
        // @formatter:off
        return this.select(FusionCondition
                .withConditionGroups(conditionGroups)
                .sort(sort)
                .build());
        // @formatter:on
    }

    @Override
    public List<T> select(FusionCondition condition) {
        Assert.notNull(condition, "FusionCondition不可以为null.");
        ConditionSql conditionSql = this.toConditionSql(condition);
        return this.jdbcRepositoryOperations.query(conditionSql.getSql(), conditionSql.getRowMapper(), conditionSql.getSqlParameterValues());
    }

    @Override
    public PageResult<T> page(FusionCondition condition, PageQuery pageQuery) {
        ConditionSql conditionSql = this.toConditionSql(condition);
        Object[] parameterValues = conditionSql.getSqlParameterValues().stream().map(SqlParameterValue::getValue).toList().toArray(Object[]::new);
        DefaultPageResult.DefaultPageResultBuilder builder = DefaultPageResult.withPageQuery(pageQuery);
        // Query Total Row Count
        String totalRowCountQuerySql = pageQuery.getTotalRowCountQuerySql(conditionSql.getSql());
        Integer totalRows = this.jdbcRepositoryOperations.queryForObject(totalRowCountQuerySql, Integer.class, parameterValues);
        if (ObjectUtils.isEmpty(totalRows) || totalRows < Constants.ONE) {
            return (PageResult<T>) builder.build();
        }
        // Query Current Page Results
        String currentPageQuerySql = pageQuery.getCurrentPageQuerySql(conditionSql.getSql());
        List<T> resultList = this.jdbcRepositoryOperations.query(currentPageQuerySql, conditionSql.getRowMapper(), parameterValues);
        // @formatter:off
        builder
                .totalRows(totalRows)
                .result(resultList)
                .build();
        // @formatter:on
        return (PageResult<T>) builder.build();
    }

    @Override
    public <R extends Serializable> PageResult<R> page(Dynamic dynamic, PageQuery pageQuery, Object... parameterValues) {
        DefaultPageResult.DefaultPageResultBuilder builder = DefaultPageResult.withPageQuery(pageQuery);
        // @formatter:off
        // Query Total Row Count
        String totalRowsQuerySql = this.formatSql(pageQuery.getTotalRowCountQuerySql(dynamic.getSql()));
        Integer totalRows = this.jdbcRepositoryOperations.queryForObject(totalRowsQuerySql, Integer.class, parameterValues);
        if (ObjectUtils.isEmpty(totalRows) || totalRows < Constants.ONE) {
            return (PageResult<R>) builder.build();
        }
        // Query Current Page Results
        String pageQuerySql = this.formatSql(pageQuery.getCurrentPageQuerySql(dynamic.getSql()));
        ResultRowMapper<R> resultRowMapper = new ResultRowMapper<>(dynamic.getColumns(), (Class<R>) dynamic.getRowMappingClass());
        List<R> resultList = this.jdbcRepositoryOperations.query(pageQuerySql, resultRowMapper, parameterValues);
        builder
                .totalRows(totalRows)
                .result(resultList)
                .build();
        // @formatter:on
        return (PageResult<R>) builder.build();
    }

    @Override
    public <R> List<R> dynamicSelect(Dynamic dynamic, Object... parameterValues) {
        Assert.notNull(dynamic, "Dynamic不可以为null.");
        Assert.notEmpty(dynamic.getColumns(), "Dynamic请至少配置一个TableColumn.");
        if (dynamic.isModifying()) {
            throw new DeviceLinksException("不允许执行Modifying自定义SQL的数据查询.");
        }
        ResultRowMapper<R> resultRowMapper = new ResultRowMapper<>(dynamic.getColumns(), (Class<R>) dynamic.getRowMappingClass());
        return this.jdbcRepositoryOperations.query(this.formatSql(dynamic.getSql()), resultRowMapper, parameterValues);
    }

    @Override
    public int dynamicModify(Dynamic dynamic, Object... parameterValues) {
        Assert.notNull(dynamic, "Dynamic不可以为null.");
        Assert.notEmpty(dynamic.getColumns(), "Dynamic请至少配置一个TableColumn.");
        Assert.notEmpty(parameterValues, "请至少传递一个DynamicParameterValue.");
        if (dynamic.getColumns().size() != parameterValues.length) {
            throw new DeviceLinksException("参数值与定义的TableColumn数量不一致.");
        }
        if (!dynamic.isModifying()) {
            throw new DeviceLinksException("不允许执行非Modifying类型的自定义SQL.");
        }
        // @formatter:off
        List<SqlParameterValue> sqlParameterValues =
                IntStream.range(Constants.ZERO, dynamic.getColumns().size())
                        .mapToObj(index -> {
                            Column column = dynamic.getColumns().get(index);
                            Object convertedValue = column.toColumnValue(parameterValues[index]);
                            return new SqlParameterValue(column.getSqlType(), convertedValue);
                        }).toList();
        // @formatter:on
        return this.jdbcRepositoryOperations.update(this.formatSql(dynamic.getSql()), sqlParameterValues);
    }

    /**
     * Format {@link Dynamic} Sql into a single line display
     *
     * @param originalSql The original sql
     * @return Formatted sql
     */
    private String formatSql(String originalSql) {
        // @formatter:off
        return SQLUtils.formatMySql(originalSql,
                new SQLUtils.FormatOption(false, false));
        // @formatter:on
    }

    /**
     * The {@link FusionCondition} Convert To {@link ConditionSql}
     *
     * @param condition {@link FusionCondition}
     * @return {@link ConditionSql}
     */
    private ConditionSql toConditionSql(FusionCondition condition) {
        ConditionSql.SelectBuilder selectBuilder = ConditionSql.select(this.table).resultType(this.mapEntityClass);
        if (!ObjectUtils.isEmpty(condition.getConditions())) {
            selectBuilder.conditions(conditions -> conditions.addAll(condition.getConditions()));
        }
        if (!ObjectUtils.isEmpty(condition.getConditionGroups())) {
            selectBuilder.conditionGroups(conditionGroups -> conditionGroups.addAll(condition.getConditionGroups()));
        }
        if (!ObjectUtils.isEmpty(condition.getSort())) {
            selectBuilder.sort(condition.getSort());
        }
        if (!ObjectUtils.isEmpty(condition.getLimit())) {
            selectBuilder.limit(condition.getLimit());
        }
        return selectBuilder.build();
    }
}
