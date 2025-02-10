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

package cn.devicelinks.console.authorization.endpoint.logout;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * The logout authentication token
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class LogoutAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private final HttpServletRequest request;

    public LogoutAuthenticationToken(String token, HttpServletRequest request) {
        super(Collections.emptyList());
        this.token = token;
        this.request = request;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }
}
