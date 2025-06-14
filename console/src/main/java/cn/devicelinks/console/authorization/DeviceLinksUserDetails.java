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

import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.DeviceLinksVersion;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.util.Collection;

/**
 * 用户详情实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class DeviceLinksUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    @Setter
    private String sessionId;

    private final UserAuthorizedAddition authorizedUserAddition;

    private DeviceLinksUserDetails(UserAuthorizedAddition authorizedUserAddition) {
        this.authorizedUserAddition = authorizedUserAddition;
    }

    public static DeviceLinksUserDetails of(UserAuthorizedAddition authorizedUserAddition) {
        Assert.notNull(authorizedUserAddition, "AuthorizedUserAddition cannot be null");
        return new DeviceLinksUserDetails(authorizedUserAddition);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return !ObjectUtils.isEmpty(authorizedUserAddition.getUser().getIdentity()) ?
                AuthorityUtils.createAuthorityList(authorizedUserAddition.getUser().getIdentity().toString()) :
                AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public String getPassword() {
        return authorizedUserAddition.getUser().getPwd();
    }

    @Override
    public String getUsername() {
        return authorizedUserAddition.getUser().getAccount();
    }

    @Override
    public boolean isEnabled() {
        return authorizedUserAddition.getUser().isEnabled();
    }
}
