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

package cn.devicelinks.console.web.query;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.sql.SortBy;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;

/**
 * 分页查询请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PaginationQuery {
    private static final String DEFAULT_SORT_PROPERTY = "id";
    private static final SortCondition DEFAULT_SORT = SortCondition.withColumn(Column.withName(DEFAULT_SORT_PROPERTY).build()).asc();
    private static final PageQuery DEFAULT_PAGE_QUERY = PageQuery.of();

    private int pageSize;

    private int page;

    @Length(max = 50)
    @Pattern(regexp = "^[\\p{L}0-9_-]+$", message = "排序属性格式不正确")
    private String sortProperty;

    @EnumValid(target = Direction.class, message = "排序顺序不允许传递非法值")
    private String sortDirection;

    public PaginationQuery(int pageSize) {
        this(pageSize, 0);
    }

    public PaginationQuery(int pageSize, int page) {
        this(pageSize, page, DEFAULT_SORT_PROPERTY, Direction.ASC.toString());
    }

    public PaginationQuery(int pageSize, int page, String sortProperty, String sortDirection) {
        this.pageSize = pageSize;
        this.page = page;
        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
    }

    public SortCondition toSortCondition() {
        if (ObjectUtils.isEmpty(sortProperty) || sortDirection == null) {
            return DEFAULT_SORT;
        } else {
            String columnName = StringUtils.lowerCamelToLowerUnder(this.sortProperty);
            return SortCondition
                    .withColumn(Column.withName(columnName).build())
                    .by(SortBy.valueOf(this.sortDirection.toLowerCase()));
        }
    }

    public PageQuery toPageQuery() {
        if (this.page <= Constants.ZERO || this.pageSize <= Constants.ZERO) {
            return DEFAULT_PAGE_QUERY;
        }
        return PageQuery.of(this.page, this.pageSize);
    }

    public enum Direction {
        ASC, DESC
    }
}
