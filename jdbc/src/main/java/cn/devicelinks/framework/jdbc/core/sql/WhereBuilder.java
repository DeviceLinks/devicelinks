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

import cn.devicelinks.framework.jdbc.core.definition.Table;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.core.utils.SqlParameterValueUtils;
import cn.devicelinks.framework.jdbc.core.utils.SqlUtils;
import lombok.Getter;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Where条件构建器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class WhereBuilder {
    public static final String WHERE = " where ";
    private final StringBuilder whereSql = new StringBuilder();
    @Getter
    private PreparedStatementSetter preparedStatementSetter;
    @Getter
    private final List<SqlParameterValue> parameterValues = new ArrayList<>();
    private final Table table;
    private List<Condition> conditions;
    private List<ConditionGroup> conditionGroups;
    private boolean appendWhere;

    private WhereBuilder(Table table) {
        this.table = table;
    }

    public static WhereBuilder withTable(Table table) {
        return new WhereBuilder(table);
    }

    public WhereBuilder conditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public WhereBuilder conditionGroups(List<ConditionGroup> conditionGroups) {
        this.conditionGroups = conditionGroups;
        return this;
    }

    public WhereBuilder build() {
        // append conditions sql
        if (!ObjectUtils.isEmpty(this.conditions)) {
            this.appendWhere();
            this.whereSql.append(SqlUtils.getConditionSql(SqlFederationAway.AND, this.conditions));
            this.parameterValues.addAll(Arrays.asList(SqlParameterValueUtils.getWithCondition(table, this.conditions)));
        }
        // append condition groups sql
        if (!ObjectUtils.isEmpty(this.conditionGroups)) {
            this.appendWhere();
            this.conditionGroups.getFirst().setFirst(true);
            this.whereSql.append(SqlUtils.getConditionGroupSql(this.conditionGroups));
            this.parameterValues.addAll(Arrays.asList(SqlParameterValueUtils.getWithConditionGroup(table, this.conditionGroups)));
        }
        // build PreparedStatementSetter
        if (!ObjectUtils.isEmpty(this.parameterValues)) {
            this.preparedStatementSetter = new ArgumentPreparedStatementSetter(this.parameterValues.toArray(SqlParameterValue[]::new));
        }
        return this;
    }

    private void appendWhere() {
        if (!this.appendWhere) {
            this.whereSql.append(WHERE);
            this.appendWhere = true;
        }
    }

    public String getSql() {
        return this.whereSql.toString();
    }
}
