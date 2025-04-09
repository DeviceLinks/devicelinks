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

package cn.devicelinks.api.support.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 更新部门请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDepartmentRequest {

    @NotEmpty(message = "部门名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "部门名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(max = 30, message = "部门名称长度最大支持30个字符")
    private String name;

    @Length(max = 32, message = "部门上级ID不可以超过32个字符")
    private String pid;

    @Range(min = 0, max = Integer.MAX_VALUE, message = "部门排序需要大于0并且小于" + Integer.MAX_VALUE)
    private int sort;

    @Length(max = 200, message = "部门描述不可以超过200个字符")
    private String description;
}
