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

package cn.devicelinks.framework.jdbc.core.sql;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * 数据过滤条件
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public final class Condition {
    private static final String CONDITION_SQL_FORMAT = "%s %s %s";
    private static final String PLACEHOLDER = "?";
    private static final String LIKE_PLACEHOLDER = "%";
    private static final String COLUMN_ALIAS_FORMAT = "%s.%s";
    @Getter
    private final SqlQueryOperator operator;
    private final ConditionValue conditionValue;
    private String tableAlias;

    private Condition(SqlQueryOperator operator, ConditionValue conditionValue) {
        this.operator = operator;
        this.conditionValue = conditionValue;
    }

    public String getColumnName() {
        return conditionValue.getColumn().getName();
    }

    public Object getParameterValue() {
        return switch (this.operator) {
            case Like -> LIKE_PLACEHOLDER + conditionValue.getValue() + LIKE_PLACEHOLDER;
            case Prefix -> conditionValue.getValue() + LIKE_PLACEHOLDER;
            case Suffix -> LIKE_PLACEHOLDER + conditionValue.getValue();
            default -> conditionValue.getValue();
        };
    }

    public String getSql() {
        // In or NotIn
        if (SqlQueryOperator.In == this.operator || SqlQueryOperator.NotIn == this.operator) {
            if (this.conditionValue.getValue() != null && this.conditionValue.getValue() instanceof Collection<?>) {
                String notInPlaceholderString = StringUtils.joiner(Constants.SEPARATOR, PLACEHOLDER, ((Collection<?>) this.conditionValue.getValue()).size());
                return String.format(CONDITION_SQL_FORMAT, this.getColumnName(), this.operator.getValue(), "(" + notInPlaceholderString + ")");
            } else {
                log.error("Failed to obtain conditional SQL, [{}]:[{}], Value is not a collection type.", this.getColumnName(), this.operator);
            }
        }
        // Format sql
        String sql = String.format(CONDITION_SQL_FORMAT, this.getColumnName(), this.operator.getValue(), PLACEHOLDER);
        // Append table alias
        return !ObjectUtils.isEmpty(this.tableAlias) ? String.format(COLUMN_ALIAS_FORMAT, this.tableAlias, sql) : sql;
    }

    public Condition tableAlias(String tableAlias) {
        Assert.hasText(tableAlias, "tableAlias cannot be null or empty");
        this.tableAlias = tableAlias;
        return this;
    }

    public static Condition withColumn(Column column, Object conditionValue) {
        return withColumn(SqlQueryOperator.EqualTo, column, conditionValue);
    }

    public static Condition withColumn(SqlQueryOperator operator, Column column, Object conditionValue) {
        Assert.notNull(operator, "The operator cannot be null");
        Assert.notNull(column, "The column cannot be null");
        return new Condition(operator, ConditionValue.with(column, conditionValue));
    }
}
