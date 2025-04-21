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

package cn.devicelinks.framework.common.authorization;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 认证端点配置抽象基础类
 * <p>
 * 所有自定义的认证端点都需要集成该类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceLinksAuthorizationEndpointConfigurer extends AbstractHttpConfigurer<DeviceLinksAuthorizationEndpointConfigurer, HttpSecurity> {
    /**
     * 获取请求匹配器
     * <p>
     * 该方法返回一个RequestMatcher对象，用于匹配需要进行认证的请求。
     * </p>
     *
     * @return RequestMatcher对象，用于匹配请求
     */
    public RequestMatcher getRequestMatcher() {
        return AnyRequestMatcher.INSTANCE;
    }
}
