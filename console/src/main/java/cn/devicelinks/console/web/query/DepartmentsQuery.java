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

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 部门查询请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DepartmentsQuery {

    @Length(max = 30, message = "部门名称不可以超过30个字符")
    private String name;

    @Length(max = 50, message = "部门标识符不可以超过50个字符")
    private String identifier;

    @Length(max = 32, message = "部门上级ID不可以超过32个字符")
    private String pid;
    
    private boolean includeChildren;
}
