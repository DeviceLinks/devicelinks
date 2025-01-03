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
import cn.devicelinks.framework.jdbc.core.definition.Column;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 排序条件
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SortCondition {
    private final Map<Column, SortBy> SortColumnMap = new HashMap<>();
    private static final String SORT_COLUMN_DELIMITER = ", ";
    private static final String SORT_SQL_PREFIX = " order by ";

    private SortCondition(Column columnName, SortBy sortBy) {
        SortColumnMap.put(columnName, sortBy);
    }

    /**
     * 根据排序条件实例化{@link SortCondition}
     *
     * @param column 列 {@link Column}
     * @return {@link SortCondition}
     */
    public static SortConditionBuilder withColumn(Column column) {
        return new SortConditionBuilder(column);
    }

    /**
     * 添加一个排序条件
     *
     * @param column 列 {@link Column}
     * @return {@link SortCondition}
     */
    public SortConditionBuilder addSort(Column column) {
        return new SortConditionBuilder(column);
    }

    /**
     * 获取格式化后的排序SQL
     *
     * @return 排序SQL
     */
    public String getSql() {
        if (!ObjectUtils.isEmpty(SortColumnMap)) {
            String sortColumnSql = this.SortColumnMap.keySet().stream().map(column -> {
                SortBy sortBy = SortColumnMap.get(column);
                return column.getName() + Constants.SPACE + sortBy.name();
            }).collect(Collectors.joining(SORT_COLUMN_DELIMITER));
            return SORT_SQL_PREFIX + sortColumnSql;
        }
        return null;
    }

    /**
     * The {@link SortCondition} Builder
     */
    public static class SortConditionBuilder {
        private final Column column;

        public SortConditionBuilder(Column column) {
            this.column = column;
        }

        public SortCondition asc() {
            return new SortCondition(column, SortBy.asc);
        }

        public SortCondition desc() {
            return new SortCondition(column, SortBy.desc);
        }
    }
}
