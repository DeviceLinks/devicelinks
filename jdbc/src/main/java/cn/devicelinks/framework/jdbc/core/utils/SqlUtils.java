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

import cn.devicelinks.framework.jdbc.core.sql.Condition;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SQL工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SqlUtils {
    /**
     * 根据查询条件拼接SQL
     *
     * @param operator   拼接每个查询条件SQL的逻辑运算符
     * @param conditions {@link Condition}对象列表
     * @return 拼接后的SQL
     */
    public static String getConditionSql(SqlFederationAway operator, List<Condition> conditions) {
        // @formatter:off
        return conditions
                .stream()
                .map(Condition::getSql)
                .collect(Collectors.joining(operator.getValue()));
        // @formatter:on
    }

    /**
     * 根据查询条件分组拼接SQL
     *
     * @param conditionGroups {@link ConditionGroup} 对象列表
     * @return 拼接后的SQL
     */
    public static String getConditionGroupSql(List<ConditionGroup> conditionGroups) {
        // @formatter:off
        return conditionGroups
                .stream()
                .map(ConditionGroup::getSql)
                .collect(Collectors.joining());
        // @formatter:on
    }
}
