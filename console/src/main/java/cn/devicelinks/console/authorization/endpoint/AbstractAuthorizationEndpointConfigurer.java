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

package cn.devicelinks.console.authorization.endpoint;

import org.springframework.security.config.annotation.ObjectPostProcessor;

/**
 * 端点认证配置抽象实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class AbstractAuthorizationEndpointConfigurer implements AuthorizationEndpointConfigurer {
    private final ObjectPostProcessor<Object> objectPostProcessor;

    protected AbstractAuthorizationEndpointConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
        this.objectPostProcessor = objectPostProcessor;
    }

    protected final <T> T postProcess(T object) {
        return (T) this.objectPostProcessor.postProcess(object);
    }

    protected final ObjectPostProcessor<Object> getObjectPostProcessor() {
        return this.objectPostProcessor;
    }
}
