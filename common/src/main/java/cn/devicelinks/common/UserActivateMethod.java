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

package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 用户激活方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum UserActivateMethod {
    /**
     * 通过向用户邮箱发送激活链接的方式
     * <p>
     * 该方式相对安全，用户访问邮箱内的激活链接，设置密码后激活账号
     */
    SendUrlToEmail("通过向用户邮箱发送激活链接的方式"),
    /**
     * 显示激活链接
     * <p>
     * 默认方式，添加用户时直接显示激活链接，复制后发送给用户
     * 用户访问激活链接，设置密码后激活账号
     */
    ShowUrl("显示激活链接");

    private final String description;

    UserActivateMethod(String description) {
        this.description = description;
    }
}
