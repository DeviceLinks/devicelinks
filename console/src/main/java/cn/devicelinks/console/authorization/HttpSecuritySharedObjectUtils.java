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

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * {@link HttpSecurity} 共享对象工具类
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public final class HttpSecuritySharedObjectUtils {

    public static AuthenticationManager getAuthenticationManager(HttpSecurity httpSecurity) {
        return httpSecurity.getSharedObject(AuthenticationManager.class);
    }

    public static ApplicationContext getApplicationContext(HttpSecurity httpSecurity) {
        return httpSecurity.getSharedObject(ApplicationContext.class);
    }

    public static <T> T getBean(HttpSecurity httpSecurity, Class<T> type) {
        return httpSecurity.getSharedObject(ApplicationContext.class).getBean(type);
    }

    public static <T> T getSharedObject(HttpSecurity httpSecurity, Class<T> type) {
        return httpSecurity.getSharedObject(type);
    }

    public static <T> T getOptionalBean(HttpSecurity httpSecurity, Class<T> type) {
        Map<String, T> beansMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                httpSecurity.getSharedObject(ApplicationContext.class), type);
        if (beansMap.size() > 1) {
            throw new NoUniqueBeanDefinitionException(type, beansMap.size(),
                    "Expected single matching bean of type '" + type.getName() + "' but found " +
                            beansMap.size() + ": " + StringUtils.collectionToCommaDelimitedString(beansMap.keySet()));
        }
        return (!beansMap.isEmpty() ? beansMap.values().iterator().next() : null);
    }
}
