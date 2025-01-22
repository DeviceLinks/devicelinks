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
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据过滤条件分组
 * 将多个{@link Condition}通过{@link SqlFederationAway}进行关联在一起
 *
 * @author 恒宇少年
 * @see SqlFederationAway
 * @see Condition
 * @since 1.0
 */
public final class ConditionGroup {
    private final SqlFederationAway groupFederationAway;
    private final SqlFederationAway conditionFederationAway;
    @Getter
    private List<Condition> conditions;
    /**
     * 是否为第一个分组
     */
    @Setter
    private boolean first;

    private ConditionGroup(SqlFederationAway groupFederationAway, SqlFederationAway conditionFederationAway, List<Condition> conditions) {
        this.groupFederationAway = groupFederationAway;
        this.conditionFederationAway = conditionFederationAway;
        this.conditions = conditions;
    }

    public String getSql() {
        if (ObjectUtils.isEmpty(conditions)) {
            return Constants.EMPTY_STRING;
        }
        StringBuffer sql = new StringBuffer();
        if (!first) {
            sql.append(this.groupFederationAway.getValue());
        }
        sql.append("(");
        sql.append(this.conditions.stream().map(Condition::getSql)
                .collect(Collectors.joining(conditionFederationAway.getValue())));
        sql.append(")");
        return sql.toString();
    }

    public static ConditionGroup withCondition(Condition... conditions) {
        return withCondition(SqlFederationAway.AND, SqlFederationAway.AND, conditions);
    }

    public static ConditionGroup withCondition(SqlFederationAway federationAway, Condition... conditions) {
        return withCondition(SqlFederationAway.AND, federationAway, conditions);
    }

    public static ConditionGroup withCondition(SqlFederationAway groupFederationAway, SqlFederationAway conditionFederationAway, Condition... conditions) {
        Assert.notEmpty(conditions, "conditions cannot be empty.");
        return new ConditionGroup(groupFederationAway, conditionFederationAway, Arrays.asList(conditions));
    }
}
