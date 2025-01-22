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

import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.AuthorizedUserAddition;
import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;
import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.common.pojos.SysUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The {@link DeviceLinksUserDetails} context
 * <p>
 * Get {@link DeviceLinksUserDetails} from the {@link SecurityContextHolder}, so it is thread-safe
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserDetailsContext {
    private static final StatusCode NO_AUTHORIZATION = StatusCode.build("NO_AUTHORIZATION", "未登录认证.");

    public static String getUserId() {
        return getCurrentUser().getId();
    }

    public static String getSessionId() {
        return getUserDetails().getSessionId();
    }

    public static SysUser getCurrentUser() {
        return getUserAddition().getUser();
    }

    public static SysDepartment getDepartment() {
        return getUserAddition().getDepartment();
    }

    public static AuthorizedUserAddition getUserAddition() {
        return getUserDetails().getAuthorizedUserAddition();
    }

    private static DeviceLinksUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new DeviceLinksAuthorizationException(NO_AUTHORIZATION);
        }
        return (DeviceLinksUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
