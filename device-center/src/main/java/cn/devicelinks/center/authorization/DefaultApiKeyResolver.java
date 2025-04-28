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

package cn.devicelinks.center.authorization;

import cn.devicelinks.component.openfeign.OpenFeignConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

/**
 * The {@link ApiKeyResolver} default impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Setter
public class DefaultApiKeyResolver implements ApiKeyResolver {
    @Override
    public ApiKeyAuthenticationRequest resolve(final HttpServletRequest request) {
        return resolveFromAuthorizationHeader(request);
    }

    private ApiKeyAuthenticationRequest resolveFromAuthorizationHeader(HttpServletRequest request) {
        String apiKey = request.getHeader(OpenFeignConstants.API_KEY_HEADER_NAME);
        String sign = request.getHeader(OpenFeignConstants.API_SIGN_HEADER_NAME);
        String timestamp = request.getHeader(OpenFeignConstants.API_TIMESTAMP_HEADER_NAME);
        return ApiKeyAuthenticationRequest.build(apiKey, sign, !ObjectUtils.isEmpty(timestamp) ? Long.parseLong(timestamp) : 0L);
    }
}
