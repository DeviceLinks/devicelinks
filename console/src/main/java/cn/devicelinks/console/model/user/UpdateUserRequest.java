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

package cn.devicelinks.console.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新用户信息请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateUserRequest {

    @NotEmpty(message = "用户名称不可以为空")
    @Length(max = 30, message = "用户名称最大允许传递30个字符串")
    private String username;

    @Email(message = "邮箱地址格式非法")
    private String email;

    @NotEmpty
    private String departmentId;

    private String phone;

    private String mark;
}
