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

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.Table;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 动态SQL封装类
 * <p>
 * 搭配自定义{@link Dynamic}查询实现动态查询，支持根据条件判断追加SQL
 * <p>
 * 示例：
 * // @formatter:off
 * {@code
 *  DynamicWrapper dynamicWrapper = DynamicWrapper
*       .select("select su.* from sys_user su left join sys_user_session sus on sus.user_id = su.id where sus.username = ?")
*       .parameters(parameters -> parameters.add("admin"))
*       .resultColumns(columns -> columns.addAll(Tables.SysUser.getTableColumns()))
*       .appendCondition(!ObjectUtils.isEmpty(deviceId), "and su.deleted = ?", false)
*       .resultType(SysUser.class)
*       .build();
*   repository.dynamicSelect(dynamicWrapper.dynamic(), dynamicWrapper.parameters());
 * }
 * // @formatter:on
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public record DynamicWrapper(Dynamic dynamic, Object[] parameters) {
    /**
     * Create {@link SelectBuilder}
     *
     * @param sql The select query sql
     * @return {@link SelectBuilder} instance
     */
    public static SelectBuilder select(String sql) {
        return new SelectBuilder(sql);
    }

    /**
     * Create {@link ModifyBuilder}
     *
     * @param sql The modify query sql
     * @return {@link ModifyBuilder} instance
     */
    public static ModifyBuilder modify(String sql) {
        return new ModifyBuilder(sql);
    }

    /**
     * The Select Builder
     */
    public static class SelectBuilder {

        // @formatter:off
        public static Consumer<Condition> NONE_CONDITION_CONSUMER = condition -> { };
        // @formatter:on

        private String sql;
        private final List<Column> resultColumns = new ArrayList<>();
        private Class<?> resultType;
        private final List<Object> parameters = new ArrayList<>();
        private final List<DynamicWhereCondition> whereConditionList = new ArrayList<>();
        private SortCondition sort;
        private LimitCondition limit;

        public SelectBuilder(String sql) {
            this.sql = sql;
        }

        public SelectBuilder resultColumns(Consumer<List<Column>> resultColumnsConsumer) {
            resultColumnsConsumer.accept(this.resultColumns);
            return this;
        }

        public SelectBuilder parameters(Consumer<List<Object>> parametersConsumer) {
            parametersConsumer.accept(this.parameters);
            return this;
        }

        public SelectBuilder resultType(Class<?> resultType) {
            this.resultType = resultType;
            return this;
        }

        public SelectBuilder sort(SortCondition sort) {
            this.sort = sort;
            return this;
        }

        public SelectBuilder limit(LimitCondition limit) {
            this.limit = limit;
            return this;
        }

        public SelectBuilder appendCondition(boolean allowAppend, SqlFederationAway federationAway, Condition condition) {
            if (allowAppend) {
                this.whereConditionList.add(DynamicWhereCondition.create(federationAway, condition.getSql(), condition.getParameterValue()));
            }
            return this;
        }

        public SelectBuilder appendCondition(boolean allowAppend, String condition, Object... parameterValues) {
            if (allowAppend) {
                this.whereConditionList.add(DynamicWhereCondition.create(condition, parameterValues));
            }
            return this;
        }

        public SelectBuilder appendSearchFieldCondition(Table table, List<SearchFieldCondition> searchFieldConditions, Consumer<Condition> consumer) {
            searchFieldConditions.forEach(searchFieldCondition -> {
                Column searchColumn = table.getColumn(searchFieldCondition.getColumnName());
                if (searchColumn == null) {
                    log.warn("The search field column: [{}] is not defined in the target TableImpl.", searchFieldCondition.getColumnName());
                }
                if (searchColumn != null && !ObjectUtils.isEmpty(searchFieldCondition.getValue())) {
                    // convert condition value
                    Condition condition = Condition.withColumn(searchFieldCondition.getOperator(), searchColumn, searchFieldCondition.getValue());
                    if (consumer != null) {
                        consumer.accept(condition);
                    }
                    this.appendCondition(!ObjectUtils.isEmpty(searchFieldCondition.getValue()), searchFieldCondition.getFederationAway(), condition);
                }
            });
            return this;
        }

        public DynamicWrapper build() {
            Assert.hasText(this.sql, "The query sql must not be empty.");
            Assert.notEmpty(this.resultColumns, "The resultColumns must not be empty.");
            Assert.notNull(this.resultType, "The resultType must not be null.");

            // append where condition sql
            this.sql = appendWhereConditionSql(this.sql, this.parameters, this.whereConditionList);

            // append sort sql
            if (!ObjectUtils.isEmpty(sort)) {
                this.sql += sort.getSql();
            }

            // append limit sql
            if (!ObjectUtils.isEmpty(limit)) {
                this.sql += limit.getSql();
            }

            return new DynamicWrapper(Dynamic.buildSelect(this.sql, this.resultColumns, this.resultType), this.parameters.toArray(Object[]::new));
        }
    }

    /**
     * The Modify Builder
     */
    public static class ModifyBuilder {
        private String sql;
        private final List<Column> parameterColumns = new ArrayList<>();
        private final List<Object> parameters = new ArrayList<>();
        private final List<DynamicWhereCondition> whereConditionList = new ArrayList<>();

        public ModifyBuilder(String sql) {
            this.sql = sql;
        }

        public ModifyBuilder parameters(Consumer<List<Column>> parametersConsumer) {
            parametersConsumer.accept(this.parameterColumns);
            return this;
        }

        public ModifyBuilder appendCondition(boolean allowAppend, SqlFederationAway federationAway, Condition condition) {
            if (allowAppend) {
                this.whereConditionList.add(DynamicWhereCondition.create(federationAway, condition.getSql(), condition.getParameterValue()));
            }
            return this;
        }

        public ModifyBuilder appendCondition(boolean allowAppend, String condition, Object... parameterValues) {
            if (allowAppend) {
                this.whereConditionList.add(DynamicWhereCondition.create(condition, parameterValues));
            }
            return this;
        }

        public DynamicWrapper build() {
            Assert.hasText(this.sql, "The modify sql must not be empty.");
            Assert.notEmpty(this.parameters, "The parameters must not be empty.");

            // append where condition sql
            this.sql = appendWhereConditionSql(this.sql, this.parameters, this.whereConditionList);

            return new DynamicWrapper(Dynamic.buildModify(this.sql, this.parameterColumns), this.parameters.toArray(Object[]::new));
        }
    }

    private static Object conditionValueConvert(Object conditionValue) {
        return conditionValue instanceof Enum<?> ? conditionValue.toString() : conditionValue;
    }

    /**
     * Check if the sql contains the where keyword
     *
     * @param sql original sql
     * @return true if the sql contains the where keyword
     */
    private static boolean hasWhereKeyword(String sql) {
        SQLStatementParser parser = new SQLStatementParser(sql);
        SQLStatement stmt = parser.parseStatement();
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) ((SQLSelectStatement) stmt).getSelect().getQuery();
        return query.getWhere() != null;
    }

    /**
     * Append sql according to conditions
     *
     * @param sql                original sql
     * @param parameters         Parameter set corresponding to the conditional placeholder in sql
     * @param whereConditionList List of where conditions
     * @return SQL after appending
     */
    private static String appendWhereConditionSql(String sql, List<Object> parameters, List<DynamicWhereCondition> whereConditionList) {
        if (!ObjectUtils.isEmpty(whereConditionList)) {
            boolean hasWhereKeyword = hasWhereKeyword(sql);
            StringBuilder whereSqlBuilder = new StringBuilder(sql);
            if (!hasWhereKeyword) {
                whereSqlBuilder = new StringBuilder(StringUtils.removeTrailingSpaces(whereSqlBuilder.toString()));
                whereSqlBuilder.append(WhereBuilder.WHERE);
            }
            for (int i = 0; i < whereConditionList.size(); i++) {
                DynamicWhereCondition condition = whereConditionList.get(i);

                // append where condition sql
                if ((!hasWhereKeyword && i == Constants.ZERO) || condition.getFederationAway() == null) {
                    whereSqlBuilder.append(condition.getConditionSql());
                } else {
                    whereSqlBuilder.append(condition.getFederationAway().getValue())
                            .append(condition.getConditionSql());
                }

                // add parameter value
                if (!ObjectUtils.isEmpty(condition.getConditionValue())) {
                    if (ObjectUtils.isArray(condition.getConditionValue())) {
                        // @formatter:off
                        List<Object> parameterValueList = Arrays
                                .stream((Object[]) condition.getConditionValue())
                                .map(DynamicWrapper::conditionValueConvert).toList();
                        // @formatter:on
                        parameters.addAll(parameterValueList);
                    } else {
                        parameters.add(conditionValueConvert(condition.getConditionValue()));
                    }
                }
            }
            return whereSqlBuilder.toString();
        }
        return sql;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class DynamicWhereCondition {

        private SqlFederationAway federationAway;

        private String conditionSql;

        private Object conditionValue;

        public static DynamicWhereCondition create(SqlFederationAway federationAway, String conditionSql, Object conditionValue) {
            return new DynamicWhereCondition(federationAway, conditionSql, conditionValue);
        }

        public static DynamicWhereCondition create(String conditionSql, Object conditionValue) {
            return new DynamicWhereCondition(null, conditionSql, conditionValue);
        }
    }
}
