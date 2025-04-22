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

package cn.devicelinks.component.web.search;

import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 检索字段查询参数实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class SearchFieldQuery {

    @NotEmpty
    @EnumValid(target = SearchFieldModuleIdentifier.class, message = "检索字段模块参数非法")
    private String searchFieldModule;

    @NotEmpty
    @EnumValid(target = SearchFieldMatch.class, message = "检索字段运算符参数非法")
    private String searchMatch = SearchFieldMatch.ALL.toString();

    @Valid
    private List<SearchFieldFilter> searchFields;
}
