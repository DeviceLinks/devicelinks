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

package cn.devicelinks.center.configuration;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

/**
 * 配置扫描{@link RegisterBean}注解的包列表
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class RegisterBeanImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    private static final String[] REGISTER_BEAN_SCAN_BASE_PACKAGES = {
            "cn.devicelinks.framework.jdbc"
    };

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        RegisterBeanClassPathBeanDefinitionScanner scanner = new RegisterBeanClassPathBeanDefinitionScanner(registry);
        scanner.doScan(REGISTER_BEAN_SCAN_BASE_PACKAGES);
        log.info("Load the @RegisterBean of the \"" + Arrays.toString(REGISTER_BEAN_SCAN_BASE_PACKAGES) + "\" package and its subpackages and register them in the IOC container.");
    }
}
