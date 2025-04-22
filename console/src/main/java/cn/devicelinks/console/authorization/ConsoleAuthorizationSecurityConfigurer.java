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

import cn.devicelinks.component.authorization.DeviceLinksAuthorizationWebSecurityConfigurer;
import cn.devicelinks.console.authorization.endpoint.login.UsernamePasswordLoginAuthenticationConfigurer;
import cn.devicelinks.console.authorization.endpoint.logout.LogoutAuthenticationConfigurer;

/**
 * 安全认证统一配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ConsoleAuthorizationSecurityConfigurer extends DeviceLinksAuthorizationWebSecurityConfigurer {
    @Override
    protected void initializeConfigurers() {
        this.configurers(configurers -> {
            // @formatter:off
            // Login Endpoint
            configurers.put(UsernamePasswordLoginAuthenticationConfigurer.class,
                    postProcess(new UsernamePasswordLoginAuthenticationConfigurer()));
            // Logout Endpoint
            configurers.put(LogoutAuthenticationConfigurer.class,
                    postProcess(new LogoutAuthenticationConfigurer()));
            // @formatter:on
        });
    }
}
