/*
 *   Copyright (C) 2024  恒宇少年
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

import cn.devicelinks.framework.common.utils.ApplicationContextUtils;
import cn.devicelinks.framework.common.utils.BeanFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.util.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 通过{@link org.springframework.context.annotation.Import}方式注册工具类Bean实例
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class UtilsBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.registerApplicationContextUtils(registry);
        this.registerBeanFactoryUtils(registry);
    }

    /**
     * Register {@link ApplicationContextUtils}
     *
     * @param registry The {@link BeanDefinitionRegistry} instance
     */
    private void registerApplicationContextUtils(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, ApplicationContextUtils.BEAN_NAME, ApplicationContextUtils.class);
    }

    /**
     * Register {@link BeanFactoryUtils}
     *
     * @param registry The {@link BeanDefinitionRegistry} instance
     */
    private void registerBeanFactoryUtils(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, BeanFactoryUtils.BEAN_NAME, BeanFactoryUtils.class);
    }
}
