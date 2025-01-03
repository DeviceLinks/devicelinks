/*
 *   Copyright (C) 2024  DeviceLinks
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
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.common.pojos.SysPosition;
import cn.devicelinks.framework.common.pojos.SysUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

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
        return getUser().getId();
    }

    public static SysUser getUser() {
        return getUserAddition().getUser();
    }

    public static String getTenantId() {
        return getUser().getTenantId();
    }

    public static SysPosition getUserPosition() {
        return getUserAddition().getPosition();
    }

    public static AuthorizedUserAddition getUserAddition() {
        return getUserDetails().getAuthorizedUserAddition();
    }

    public static List<SysGroup> getUserGroups() {
        return getUserAddition().getUserGroups();
    }

    private static DeviceLinksUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new DeviceLinksAuthorizationException(NO_AUTHORIZATION);
        }
        return (DeviceLinksUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
