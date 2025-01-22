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
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
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
        private String sql;
        private final List<Column> resultColumns = new ArrayList<>();
        private Class<?> resultType;
        private final List<Object> parameters = new ArrayList<>();
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
                this.sql += federationAway.getValue() + condition.getSql();
                this.parameters.add(conditionValueConvert(condition.getParameterValue()));
            }
            return this;
        }

        public SelectBuilder appendCondition(boolean allowAppend, String condition, Object... parameterValues) {
            if (allowAppend) {
                this.sql += Constants.SPACE + condition;
                if (!ObjectUtils.isEmpty(parameterValues)) {
                    // @formatter:off
                    List<Object> parameterValueList = Arrays.stream(parameterValues)
                            .map(DynamicWrapper::conditionValueConvert).toList();
                    // @formatter:on
                    this.parameters.addAll(parameterValueList);
                }
            }
            return this;
        }

        public DynamicWrapper build() {
            Assert.hasText(this.sql, "The query sql must not be empty.");
            Assert.notEmpty(this.resultColumns, "The resultColumns must not be empty.");
            Assert.notNull(this.resultType, "The resultType must not be null.");
            Assert.notEmpty(this.parameters, "The parameters must not be empty.");
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

        public ModifyBuilder(String sql) {
            this.sql = sql;
        }

        public ModifyBuilder parameters(Consumer<List<Column>> parametersConsumer) {
            parametersConsumer.accept(this.parameterColumns);
            return this;
        }

        public ModifyBuilder appendCondition(boolean allowAppend, SqlFederationAway federationAway, Condition condition) {
            if (allowAppend) {
                this.sql += federationAway.getValue() + condition.getSql();
                this.parameters.add(conditionValueConvert(condition.getParameterValue()));
            }
            return this;
        }

        public ModifyBuilder appendCondition(boolean allowAppend, String condition, Object... parameterValues) {
            if (allowAppend) {
                this.sql += condition;
                if (!ObjectUtils.isEmpty(parameterValues)) {
                    // @formatter:off
                    List<Object> parameterValueList = Arrays.stream(parameterValues)
                            .map(DynamicWrapper::conditionValueConvert).toList();
                    // @formatter:on
                    this.parameters.addAll(parameterValueList);
                }
            }
            return this;
        }

        public DynamicWrapper build() {
            Assert.hasText(this.sql, "The modify sql must not be empty.");
            Assert.notEmpty(this.parameters, "The parameters must not be empty.");
            return new DynamicWrapper(Dynamic.buildModify(this.sql, this.parameterColumns), this.parameters.toArray(Object[]::new));
        }
    }

    private static Object conditionValueConvert(Object conditionValue) {
        return conditionValue instanceof Enum<?> ? conditionValue.toString() : conditionValue;
    }
}
