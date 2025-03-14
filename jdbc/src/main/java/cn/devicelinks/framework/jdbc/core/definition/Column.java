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

package cn.devicelinks.framework.jdbc.core.definition;

import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.jdbc.core.annotation.IdGenerationStrategy;
import cn.devicelinks.framework.jdbc.core.mapper.value.BasicColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.mapper.value.ColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.sql.Condition;
import cn.devicelinks.framework.jdbc.core.sql.ConditionValue;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.*;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 数据库表中单个列定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@AllArgsConstructor
@ToString
@Getter
public class Column {

    private static final String COLUMN_ALIAS_FORMAT = "%s.%s";

    private final String name;

    private boolean pk;

    private boolean insertable;

    private boolean updatable;

    private int sqlType;

    @Setter(AccessLevel.PRIVATE)
    private ColumnValueMapper columnValueMapper;
    /**
     * The PrivateKey generation strategy for the column value
     */
    private IdGenerationStrategy idGenerationStrategy;
    /**
     * Get the default value, only used for insert data
     */
    private Supplier<Object> defaultValueSupplier;

    /**
     * Create {@link ColumnBuilder} Instance
     *
     * @param columnName The Column Name
     * @return {@link ColumnBuilder}
     */
    public static ColumnBuilder withName(String columnName) {
        return new ColumnBuilder(columnName);
    }

    /**
     * Convert the original value through the {@link ColumnValueMapper}
     * <p>
     * The converted value is generally used to write to the database
     *
     * @param originalValue The original value
     * @return Converted value
     */
    public Object toColumnValue(Object originalValue) {
        return this.columnValueMapper.toColumn(originalValue, this.getName());
    }

    /**
     * Get column values from {@link ResultSet} and convert according to {@link ColumnValueMapper}
     *
     * @param rs         {@link ResultSet}
     * @param columnName The column name
     * @return Converted value
     * @throws SQLException The SqlException
     */
    public Object fromColumnValue(ResultSet rs, String columnName) throws SQLException {
        return this.columnValueMapper.fromColumn(rs, columnName);
    }

    public String getUpperCamelName() {
        return StringUtils.lowerUnderToUpperCamel(this.name.replace("`", ""));
    }

    public String getLowerCamelName() {
        return StringUtils.lowerUnderToLowerCamel(this.name);
    }

    /**
     * Use the current column to perform desc sorting
     *
     * @return {@link SortCondition}
     */
    public SortCondition desc() {
        return SortCondition.withColumn(this).desc();
    }

    /**
     * Use the current column to perform asc sorting
     *
     * @return {@link SortCondition}
     */
    public SortCondition asc() {
        return SortCondition.withColumn(this).asc();
    }

    /**
     * Use this column create {@link SqlQueryOperator#EqualTo} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition eq(Object value) {
        return condition(SqlQueryOperator.EqualTo, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#NotEqualTo} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition neq(Object value) {
        return condition(SqlQueryOperator.NotEqualTo, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#GreaterThan} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition gt(Object value) {
        return condition(SqlQueryOperator.GreaterThan, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#GreaterThanOrEqualTo} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition gte(Object value) {
        return condition(SqlQueryOperator.GreaterThanOrEqualTo, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#LessThan} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition lt(Object value) {
        return condition(SqlQueryOperator.LessThan, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#Like} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition like(Object value) {
        return condition(SqlQueryOperator.Like, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#NotLike} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition notLike(Object value) {
        return condition(SqlQueryOperator.NotLike, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#Prefix} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition prefix(String value) {
        return condition(SqlQueryOperator.Prefix, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#Suffix} condition
     *
     * @param value condition parameter value
     * @return {@link Condition}
     */
    public Condition suffix(String value) {
        return condition(SqlQueryOperator.Suffix, value);
    }

    /**
     * Use this column create {@link SqlQueryOperator#In} condition
     *
     * @param values condition parameter values
     * @return {@link Condition}
     */
    public Condition in(List<Object> values) {
        Assert.notEmpty(values, "查询条件" + this.getName() + ",In集合不可以为空.");
        return condition(SqlQueryOperator.In, values);
    }

    /**
     * Use this column create {@link SqlQueryOperator#NotIn} condition
     *
     * @param values condition parameter values
     * @return {@link Condition}
     */
    public Condition notIn(List<Object> values) {
        Assert.notEmpty(values, "查询条件" + this.getName() + ",NotIn集合不可以为空.");
        return condition(SqlQueryOperator.NotIn, values);
    }

    /**
     * Use this column add to condition collection
     *
     * @param operator Comparison operator
     * @param value    The condition value
     * @return {@link Condition}
     */
    private Condition condition(SqlQueryOperator operator, Object value) {
        return Condition.withColumn(operator, this, value);
    }

    /**
     * Use this column create condition value instance
     *
     * @param value The condition value
     * @return {@link ConditionValue}
     */
    public ConditionValue set(Object value) {
        return ConditionValue.with(this, value);
    }

    public String getName(String tableAlias) {
        if (ObjectUtils.isEmpty(tableAlias)) {
            return this.getName();
        }
        return String.format(COLUMN_ALIAS_FORMAT, tableAlias, this.getName());
    }

    /**
     * The {@link Column} Builder
     */
    public static class ColumnBuilder {
        private final String name;
        private boolean primaryKey;
        private boolean insertable;
        private boolean updatable;
        private int sqlType;
        private ColumnValueMapper columnValueMapper;
        private IdGenerationStrategy idGenerationStrategy;
        private Supplier<Object> defaultValueSupplier;

        public ColumnBuilder(String name) {
            Assert.hasText(name, "The Column name must not be empty.");
            this.name = name;
            this.sqlType = Types.VARCHAR;
            this.insertable = true;
            this.updatable = true;
            this.columnValueMapper = BasicColumnValueMapper.STRING;
        }

        public ColumnBuilder primaryKey() {
            this.primaryKey = true;
            this.idGenerationStrategy = IdGenerationStrategy.OBJECT_ID;
            return this;
        }

        public ColumnBuilder primaryKey(IdGenerationStrategy idGenerationStrategy) {
            this.primaryKey = true;
            this.idGenerationStrategy = Objects.requireNonNullElse(idGenerationStrategy, IdGenerationStrategy.OBJECT_ID);
            return this;
        }

        public ColumnBuilder notInsertable() {
            this.insertable = true;
            return this;
        }

        public ColumnBuilder notUpdatable() {
            this.updatable = true;
            return this;
        }

        public ColumnBuilder sqlType(int sqlType) {
            this.sqlType = sqlType;
            return this;
        }

        public ColumnBuilder typeMapper(ColumnValueMapper columnValueMapper) {
            this.columnValueMapper = columnValueMapper;
            return this;
        }

        public ColumnBuilder notOperate() {
            this.insertable = false;
            this.updatable = false;
            return this;
        }

        public ColumnBuilder booleanValue() {
            this.sqlType = Types.BOOLEAN;
            this.columnValueMapper = BasicColumnValueMapper.BOOLEAN;
            return this;
        }

        public ColumnBuilder intValue() {
            this.sqlType = Types.INTEGER;
            this.columnValueMapper = BasicColumnValueMapper.INTEGER;
            return this;
        }

        public ColumnBuilder longValue() {
            this.sqlType = Types.BIGINT;
            this.columnValueMapper = BasicColumnValueMapper.LONG;
            return this;
        }

        public ColumnBuilder localDateValue() {
            this.sqlType = Types.DATE;
            this.columnValueMapper = BasicColumnValueMapper.LOCAL_DATE;
            return this;
        }

        public ColumnBuilder localTimeValue() {
            this.sqlType = Types.TIME;
            this.columnValueMapper = BasicColumnValueMapper.LOCAL_TIME;
            return this;
        }

        public ColumnBuilder localDateTimeValue() {
            this.sqlType = Types.TIMESTAMP;
            this.columnValueMapper = BasicColumnValueMapper.LOCAL_DATE_TIME;
            return this;
        }

        public ColumnBuilder timestamp() {
            this.sqlType = Types.TIMESTAMP;
            this.columnValueMapper = BasicColumnValueMapper.TIMESTAMP;
            return this;
        }

        public ColumnBuilder defaultValue(Supplier<Object> defaultValueSupplier) {
            this.defaultValueSupplier = defaultValueSupplier;
            return this;
        }

        public Column build() {
            return new Column(name, primaryKey, insertable, updatable, sqlType, columnValueMapper, idGenerationStrategy, defaultValueSupplier);
        }
    }
}
