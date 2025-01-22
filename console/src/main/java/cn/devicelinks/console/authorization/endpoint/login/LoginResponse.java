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

package cn.devicelinks.console.authorization.endpoint.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The login response
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class LoginResponse {
    /**
     * The token value
     */
    private String token;
    /**
     * The token expires seconds
     */
    @JsonProperty("expires_in")
    private long expiresIn;
    /**
     * Is the login successful
     */
    private boolean success;
    /**
     * The response message
     */
    private String message;

    public static LoginResponse error(String message) {
        return new LoginResponse(null, -1, false, message);
    }

    public static LoginResponse success(String token, long expiresIn) {
        return new LoginResponse(token, expiresIn, true, "login success");
    }
}
