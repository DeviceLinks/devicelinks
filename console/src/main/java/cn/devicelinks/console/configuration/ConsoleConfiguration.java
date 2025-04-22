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

package cn.devicelinks.console.configuration;

import cn.devicelinks.component.web.RequestContextWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 控制台配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
public class ConsoleConfiguration {

    /**
     * 创建并返回一个 {@link RequestContextWebFilter} 实例。
     * <p>
     * 此方法用于配置请求上下文过滤器，该过滤器在每个请求处理过程中提供请求上下文的支持。
     * 通过此过滤器，可以在请求的生命周期内存储和访问与请求相关的信息。
     *
     * @return 返回一个新的 {@link RequestContextWebFilter} 实例。
     */
    @Bean
    public RequestContextWebFilter requestContextWebFilter() {
        return new RequestContextWebFilter();
    }
}