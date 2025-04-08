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

package cn.devicelinks.console.authorization;

import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户附加信息加载类接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface AuthorizedUserAdditionService {
    /**
     * 根据用户名称加载附加信息
     *
     * @param username 用户名{@link UserDetails#getUsername()}
     * @return {@link UserAuthorizedAddition}
     */
    UserAuthorizedAddition selectByUsername(String username);
}
