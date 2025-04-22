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

package cn.devicelinks.console.authorization.endpoint.access;

import cn.devicelinks.console.authorization.DeviceLinksUserDetails;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * The resource access authentication token
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ResourceTokenAccessAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private DeviceLinksUserDetails deviceLinksUserDetails;

    public ResourceTokenAccessAuthenticationToken(String token) {
        super(Collections.emptyList());
        Assert.hasLength(token, "Token cannot be null or empty");
        this.token = token;
    }

    public ResourceTokenAccessAuthenticationToken(String token, DeviceLinksUserDetails deviceLinksUserDetails) {
        super(deviceLinksUserDetails.getAuthorities());
        Assert.hasLength(token, "Token cannot be null or empty");
        Assert.notNull(deviceLinksUserDetails, "DeviceLinksUserDetails cannot be null");
        this.token = token;
        this.deviceLinksUserDetails = deviceLinksUserDetails;
    }


    public static ResourceTokenAccessAuthenticationToken unauthenticated(String token) {
        return new ResourceTokenAccessAuthenticationToken(token);
    }

    public static ResourceTokenAccessAuthenticationToken authenticated(String token, DeviceLinksUserDetails deviceLinksUserDetails) {
        ResourceTokenAccessAuthenticationToken resourceTokenAccessAuthenticationToken = new ResourceTokenAccessAuthenticationToken(token, deviceLinksUserDetails);
        resourceTokenAccessAuthenticationToken.setAuthenticated(true);
        return resourceTokenAccessAuthenticationToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.deviceLinksUserDetails;
    }
}
