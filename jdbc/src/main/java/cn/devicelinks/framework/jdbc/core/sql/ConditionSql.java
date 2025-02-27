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

package cn.devicelinks.framework.jdbc.core.sql;

import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.EntityStructure;
import cn.devicelinks.framework.jdbc.core.definition.Table;
import cn.devicelinks.framework.jdbc.core.mapper.TableResultRowMapper;
import cn.devicelinks.framework.jdbc.core.utils.SqlParameterValueUtils;
import lombok.Getter;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 条件SQL封装类
 *
 * @author 恒宇少年
 * @see Condition
 * @see ConditionGroup
 * @since 1.0
 */
@Getter
public class ConditionSql {
    private final String sql;
    private final PreparedStatementSetter preparedStatementSetter;
    private final List<SqlParameterValue> sqlParameterValues;
    private final TableResultRowMapper rowMapper;

    public ConditionSql(String sql, PreparedStatementSetter preparedStatementSetter, List<SqlParameterValue> sqlParameterValues) {
        this(sql, preparedStatementSetter, sqlParameterValues, null);
    }

    public ConditionSql(String sql, PreparedStatementSetter preparedStatementSetter, List<SqlParameterValue> sqlParameterValues, TableResultRowMapper rowMapper) {
        this.sql = sql;
        this.preparedStatementSetter = preparedStatementSetter;
        this.sqlParameterValues = sqlParameterValues;
        this.rowMapper = rowMapper;
    }

    public static SelectBuilder select(Table table) {
        return new SelectBuilder(table);
    }

    public static DeleteBuilder delete(Table table) {
        return new DeleteBuilder(table);
    }

    public static UpdateBuilder update(Table table) {
        return new UpdateBuilder(table);
    }

    /**
     * Select Builder
     */
    public static class SelectBuilder {
        private final StringBuilder sql;
        private TableResultRowMapper rowMapper;
        private final Table table;
        private final List<Condition> conditions = new ArrayList<>();
        private final List<ConditionGroup> conditionGroups = new ArrayList<>();
        private SortCondition sort;
        private LimitCondition limit;
        private EntityStructure structure;
        private Class<?> resultType;

        public SelectBuilder(Table table) {
            Assert.notNull(table, "table must not be null");
            this.table = table;
            this.sql = new StringBuilder(this.table.getQuerySql());
        }

        public SelectBuilder resultType(Class<?> resultType) {
            Assert.notNull(resultType, "resultType must not be null");
            this.resultType = resultType;
            return this;
        }

        public SelectBuilder condition(Condition condition) {
            Assert.notNull(condition, "condition must not be null");
            this.conditions.add(condition);
            return this;
        }

        public SelectBuilder conditions(Consumer<List<Condition>> conditionConsumer) {
            conditionConsumer.accept(this.conditions);
            return this;
        }

        public SelectBuilder conditionGroup(ConditionGroup conditionGroup) {
            Assert.notNull(conditionGroup, "conditionGroup must not be null");
            this.conditionGroups.add(conditionGroup);
            return this;
        }

        public SelectBuilder conditionGroups(Consumer<List<ConditionGroup>> conditionGroupConsumer) {
            conditionGroupConsumer.accept(this.conditionGroups);
            return this;
        }

        public SelectBuilder sort(SortCondition sort) {
            Assert.notNull(sort, "sort must not be null");
            this.sort = sort;
            return this;
        }

        public SelectBuilder limit(LimitCondition limit) {
            Assert.notNull(limit, "limit must not be null");
            this.limit = limit;
            return this;
        }

        public SelectBuilder structure(EntityStructure structure) {
            Assert.notNull(structure, "structure must not be null");
            this.structure = structure;
            return this;
        }

        public ConditionSql build() {
            if (this.structure != null) {
                this.rowMapper = new TableResultRowMapper(this.structure);
            } else {
                this.rowMapper = new TableResultRowMapper<>(this.table, resultType);
            }
            // @formatter:off
            WhereBuilder whereBuilder = WhereBuilder
                    .withTable(this.table)
                    .conditions(this.conditions)
                    .conditionGroups(this.conditionGroups)
                    .build();
            // @formatter:on
            // append condition sql
            if (!ObjectUtils.isEmpty(whereBuilder.getSql())) {
                this.sql.append(whereBuilder.getSql());
            }
            // append sort sql
            if (!ObjectUtils.isEmpty(sort)) {
                this.sql.append(sort.getSql());
            }
            // append limit sql
            if (!ObjectUtils.isEmpty(limit)) {
                this.sql.append(limit.getSql());
            }
            return new ConditionSql(this.sql.toString(), whereBuilder.getPreparedStatementSetter(), whereBuilder.getParameterValues(), this.rowMapper);
        }
    }

    /**
     * Update Builder
     */
    public static class UpdateBuilder {
        private final Table table;
        // 为空时更新全部列
        private final List<Column> updateColumns = new ArrayList<>();
        private final List<SqlParameterValue> parameterValues = new ArrayList<>();
        private final List<Condition> conditions = new ArrayList<>();
        private final List<ConditionGroup> conditionGroups = new ArrayList<>();
        private String filterSql;
        private final List<ConditionValue> filterConditionValues = new ArrayList<>();

        public UpdateBuilder(Table table) {
            Assert.notNull(table, "table must not be null");
            this.table = table;
        }

        public UpdateBuilder columns(Consumer<List<Column>> columnsConsumer) {
            columnsConsumer.accept(this.updateColumns);
            return this;
        }

        public UpdateBuilder columnValues(Consumer<List<SqlParameterValue>> columnValuesConsumer) {
            columnValuesConsumer.accept(this.parameterValues);
            return this;
        }

        public UpdateBuilder condition(Condition condition) {
            Assert.notNull(condition, "condition must not be null");
            this.conditions.add(condition);
            return this;
        }

        public UpdateBuilder conditions(Consumer<List<Condition>> conditionConsumer) {
            conditionConsumer.accept(this.conditions);
            return this;
        }

        public UpdateBuilder conditionGroup(ConditionGroup conditionGroup) {
            Assert.notNull(conditionGroup, "conditionGroup must not be null");
            this.conditionGroups.add(conditionGroup);
            return this;
        }

        public UpdateBuilder conditionGroups(Consumer<List<ConditionGroup>> conditionGroupConsumer) {
            conditionGroupConsumer.accept(this.conditionGroups);
            return this;
        }

        public UpdateBuilder filterSql(String filterSql) {
            Assert.hasText(filterSql, "filterSql must not be empty");
            this.filterSql = filterSql;
            return this;
        }

        public UpdateBuilder filterConditionValues(Consumer<List<ConditionValue>> conditionValueConsumer) {
            conditionValueConsumer.accept(this.filterConditionValues);
            return this;
        }

        public ConditionSql build() {
            // @formatter:off
            WhereBuilder whereBuilder = WhereBuilder
                    .withTable(this.table)
                    .conditions(this.conditions)
                    .conditionGroups(this.conditionGroups)
                    .build();
            // @formatter:on
            StringBuilder sql = new StringBuilder(ObjectUtils.isEmpty(updateColumns) ? this.table.getUpdateSql() : this.table.getUpdateSql(this.updateColumns));
            // append condition sql
            if (!ObjectUtils.isEmpty(whereBuilder.getSql())) {
                sql.append(whereBuilder.getSql());
            }
            // append condition parameters
            if (!ObjectUtils.isEmpty(whereBuilder.getParameterValues())) {
                this.parameterValues.addAll(whereBuilder.getParameterValues());
            }
            // append filter sql
            if (!ObjectUtils.isEmpty(this.filterSql)) {
                sql.append(this.filterSql);
            }
            // add condition values to parameter values
            if (!ObjectUtils.isEmpty(this.filterConditionValues)) {
                this.parameterValues.addAll(Arrays.asList(SqlParameterValueUtils.getWithColumnValue(this.table, this.filterConditionValues)));
            }
            // rebuild PreparedStatementSetter
            PreparedStatementSetter preparedStatementSetter = new ArgumentPreparedStatementSetter(this.parameterValues.toArray(SqlParameterValue[]::new));
            return new ConditionSql(sql.toString(), preparedStatementSetter, this.parameterValues);
        }
    }

    /**
     * Delete Builder
     */
    public static class DeleteBuilder {
        private final StringBuilder sql;
        private final Table table;
        private final List<Condition> conditions = new ArrayList<>();
        private final List<ConditionGroup> conditionGroups = new ArrayList<>();
        private String filterSql;
        private final List<ConditionValue> filterConditionValues = new ArrayList<>();

        public DeleteBuilder(Table table) {
            Assert.notNull(table, "table must not be null");
            this.table = table;
            this.sql = new StringBuilder(this.table.getDeleteSql());
        }

        public DeleteBuilder condition(Condition condition) {
            Assert.notNull(condition, "condition must not be null");
            this.conditions.add(condition);
            return this;
        }

        public DeleteBuilder conditions(Consumer<List<Condition>> conditionConsumer) {
            conditionConsumer.accept(this.conditions);
            return this;
        }

        public DeleteBuilder conditionGroup(ConditionGroup conditionGroup) {
            Assert.notNull(conditionGroup, "conditionGroup must not be null");
            this.conditionGroups.add(conditionGroup);
            return this;
        }

        public DeleteBuilder conditionGroups(Consumer<List<ConditionGroup>> conditionGroupConsumer) {
            conditionGroupConsumer.accept(this.conditionGroups);
            return this;
        }

        public DeleteBuilder filterSql(String filterSql) {
            Assert.hasText(filterSql, "filterSql must not be empty");
            this.filterSql = filterSql;
            return this;
        }

        public DeleteBuilder filterConditionValues(Consumer<List<ConditionValue>> conditionValueConsumer) {
            conditionValueConsumer.accept(this.filterConditionValues);
            return this;
        }

        public ConditionSql build() {
            // @formatter:off
            WhereBuilder whereBuilder = WhereBuilder
                    .withTable(this.table)
                    .conditions(this.conditions)
                    .conditionGroups(this.conditionGroups)
                    .build();
            // @formatter:on
            // append condition sql
            if (!ObjectUtils.isEmpty(whereBuilder.getSql())) {
                this.sql.append(whereBuilder.getSql());
            }
            // append filter sql
            if (!ObjectUtils.isEmpty(this.filterSql)) {
                this.sql.append(this.filterSql);
            }
            List<SqlParameterValue> parameterValues = whereBuilder.getParameterValues();
            // add condition values to parameter values
            if (!ObjectUtils.isEmpty(this.filterConditionValues)) {
                parameterValues.addAll(Arrays.asList(SqlParameterValueUtils.getWithColumnValue(this.table, this.filterConditionValues)));
            }
            // rebuild PreparedStatementSetter
            PreparedStatementSetter preparedStatementSetter = whereBuilder.getPreparedStatementSetter();
            if (!ObjectUtils.isEmpty(parameterValues) && !ObjectUtils.isEmpty(this.filterConditionValues)) {
                preparedStatementSetter = new ArgumentPreparedStatementSetter(parameterValues.toArray(SqlParameterValue[]::new));
            }
            return new ConditionSql(this.sql.toString(), preparedStatementSetter, parameterValues);
        }
    }
}
