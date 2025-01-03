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

package cn.devicelinks.console.authorization.endpoint.login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;

/**
 * The login authentication token
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class UsernamePasswordLoginAuthenticationToken extends AbstractAuthenticationToken {
    private HttpServletRequest request;
    private final String username;
    private String password;
    private String token;
    private long expiresIn;

    public UsernamePasswordLoginAuthenticationToken(HttpServletRequest request, String username, String password) {
        super(Collections.emptyList());
        this.request = request;
        this.username = username;
        this.password = password;
        this.setAuthenticated(false);
    }

    public UsernamePasswordLoginAuthenticationToken(String username, String password,
                                                    Collection<? extends GrantedAuthority> authorities, String token, long expiresIn) {
        super(authorities);
        this.username = username;
        this.password = password;
        this.token = token;
        this.expiresIn = expiresIn;
        super.setAuthenticated(true);
    }

    public static UsernamePasswordLoginAuthenticationToken unauthenticated(HttpServletRequest request, String username, String password) {
        return new UsernamePasswordLoginAuthenticationToken(request, username, password);
    }

    public static UsernamePasswordLoginAuthenticationToken authenticated(String username, String password,
                                                                         Collection<? extends GrantedAuthority> authorities, String token, long expiresIn) {
        return new UsernamePasswordLoginAuthenticationToken(username, password, authorities, token, expiresIn);
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.password = null;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}
