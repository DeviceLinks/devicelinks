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

package cn.devicelinks.jdbc.core.page;

import lombok.Getter;

/**
 * 分页查询参数类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class PageQuery {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_INDEX = 1;
    private static final String COUNT_SQL_FORMAT = "select count(0) from (%s) temp_count";
    private static final String PAGE_SQL_FORMAT = "%s limit %d offset %d";
    private final int pageIndex;
    private final int pageSize;

    private PageQuery(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public static PageQuery of() {
        return new PageQuery(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
    }

    public static PageQuery of(int pageIndex) {
        return new PageQuery(pageIndex, DEFAULT_PAGE_SIZE);
    }

    public static PageQuery of(int pageIndex, int pageSize) {
        return new PageQuery(pageIndex, pageSize);
    }

    /**
     * 获取当前页查询SQL
     *
     * @param conditionSQL 原始的条件查询SQL
     * @return 查询当前页数据的SQL
     */
    public String getCurrentPageQuerySql(String conditionSQL) {
        int offset = (this.pageIndex - 1) * this.pageSize;
        return String.format(PAGE_SQL_FORMAT, conditionSQL, pageSize, offset);
    }

    /**
     * 获取未分页总条数查询SQL
     *
     * @param conditionSQL 原始的条件查询SQL
     * @return 查询总条数时使用的SQL
     */
    public String getTotalRowCountQuerySql(String conditionSQL) {
        return String.format(COUNT_SQL_FORMAT, conditionSQL);
    }
}
