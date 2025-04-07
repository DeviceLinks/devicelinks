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

package cn.devicelinks.core.configuration;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link RegisterBean}注解扫描自动化配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ConditionalOnClass(RegisterBean.class)
@Import(RegisterBeanImportBeanDefinitionRegistrar.class)
@Configuration
public class RegisterBeanAutoConfiguration {
    //...
}
