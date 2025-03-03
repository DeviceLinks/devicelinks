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

import cn.devicelinks.framework.common.web.SearchField;
import cn.devicelinks.framework.common.web.SearchFieldOperator;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 检索字段过滤实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class SearchFieldFilter {
    /**
     * 检索字段
     *
     * @see SearchField#getField()
     */
    @NotEmpty(message = "检索字段不能为空")
    private String field;
    /**
     * 检索字段运算符
     */
    @NotEmpty(message = "检索字段运算符不能为空")
    @EnumValid(target = SearchFieldOperator.class, message = "检索字段运算符参数值非法")
    private String operator;
    /**
     * 检索字段值
     */
    @NotNull(message = "检索字段值不能为空")
    private Object value;
}
