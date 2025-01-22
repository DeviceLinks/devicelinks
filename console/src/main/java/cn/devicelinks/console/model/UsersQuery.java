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

import cn.devicelinks.framework.common.UserIdentity;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 获取用户列表请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UsersQuery {

    @Length(max = 50)
    private String name;

    @Length(max = 32)
    private String departmentId;

    @EnumValid(target = UserIdentity.class, message = "用户身份不允许传递非法值")
    private String identity;
}
