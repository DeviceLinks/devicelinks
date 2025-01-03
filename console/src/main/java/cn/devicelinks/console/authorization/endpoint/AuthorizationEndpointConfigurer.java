/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.console.authorization.endpoint;

import jakarta.servlet.Filter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 端点认证配置接口
 *
 * @author 恒宇少年
 */
public interface AuthorizationEndpointConfigurer {
    /**
     * 初始化认证
     * <p>
     * 注册{@link AuthenticationProvider}
     * 通过{@link HttpSecurity#authenticationProvider(AuthenticationProvider)}方法添加
     *
     * @param httpSecurity {@link HttpSecurity}
     */
    void init(HttpSecurity httpSecurity);

    /**
     * 配置认证
     * <p>
     * 注册{@link OncePerRequestFilter}
     * 通过{@link HttpSecurity#addFilter(Filter)}方法添加
     *
     * @param httpSecurity {@link HttpSecurity}
     */
    void configure(HttpSecurity httpSecurity);

    /**
     * 返回认证端点的请求匹配器
     *
     * @return {@link RequestMatcher}
     */
    RequestMatcher getRequestMatcher();
}
