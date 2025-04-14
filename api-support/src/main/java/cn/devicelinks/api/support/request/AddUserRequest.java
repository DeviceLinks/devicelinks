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

import cn.devicelinks.framework.common.UserActivateMethod;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加用户请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddUserRequest {

    @NotEmpty(message = "用户名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "用户名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(max = 30, message = "用户名称最大允许传递30个字符串")
    private String name;

    @NotEmpty(message = "用户账号不可以为空")
    @Pattern(regexp = "^[a-zA-Z0-9@][a-zA-Z0-9_@]*$", message = "用户账号仅支持大小写字母、数字、下划线、@，且不允许以下划线开头。")
    @Length(max = 30, message = "用户账号最大允许传递30个字符串")
    private String account;

    @NotNull(message = "用户激活方式不可以为空")
    @EnumValid(target = UserActivateMethod.class, message = "用户激活方式不允许传递非法值")
    private String activateMethod;

    @Email(message = "邮箱地址格式非法")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$|^$", message = "请输入有效的手机号码")
    private String phone;

    private String departmentId;

    private String mark;
}
