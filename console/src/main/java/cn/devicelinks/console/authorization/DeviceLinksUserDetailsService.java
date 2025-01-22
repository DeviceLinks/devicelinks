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

import cn.devicelinks.framework.common.authorization.AuthorizedUserAddition;
import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * {@link UserDetailsService}JDBC方式实现
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceLinksUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthorizedUserAdditionService authorizedUserAdditionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthorizedUserAddition authorizedUserAddition = authorizedUserAdditionService.selectByUsername(username);
        return DeviceLinksUserDetails.of(authorizedUserAddition);
    }
}
