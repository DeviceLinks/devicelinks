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

package cn.devicelinks.console.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 新增部门请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDepartmentRequest {

    @NotEmpty(message = "部门名称不可以为空")
    @Length(max = 30, message = "部门名称不可以超过30个字符")
    private String name;

    @NotEmpty(message = "部门标识符不可以为空")
    @Length(max = 50, message = "部门标识符不可以超过50个字符")
    private String identifier;

    @Length(max = 32, message = "部门上级ID不可以超过32个字符")
    private String pid;

    @Range(min = 0, max = Integer.MAX_VALUE, message = "部门排序需要大于0并且小于" + Integer.MAX_VALUE)
    private int sort;

    @Range(min = 0, max = Integer.MAX_VALUE, message = "部门等级需要大于0并且小于" + Integer.MAX_VALUE)
    private int level;

    @Length(max = 200, message = "部门描述不可以超过200个字符")
    private String description;
}
