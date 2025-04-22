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

package cn.devicelinks.api.model.query;

import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 分页查询请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PaginationQuery {
    /**
     * The default sort property
     */
    public static final String DEFAULT_SORT_PROPERTY = "id";

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

    public enum Direction {
        ASC, DESC
    }
}
