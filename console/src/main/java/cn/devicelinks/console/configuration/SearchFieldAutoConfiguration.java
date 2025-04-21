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

import cn.devicelinks.framework.common.web.search.annotation.SearchModule;
import cn.devicelinks.framework.common.web.search.SearchModuleAnnotationMethodInterceptor;
import cn.devicelinks.framework.common.web.search.SearchModuleAnnotationPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 检索字段自动化配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ConditionalOnClass({SearchModuleAnnotationMethodInterceptor.class, SearchModuleAnnotationPointcutAdvisor.class})
@Configuration
public class SearchFieldAutoConfiguration {
    /**
     * 检索字段注解{@link SearchModule}方法拦截器
     *
     * @return {@link SearchModuleAnnotationMethodInterceptor}
     */
    @Bean
    public SearchModuleAnnotationMethodInterceptor searchModuleAnnotationMethodInterceptor() {
        return new SearchModuleAnnotationMethodInterceptor();
    }

    /**
     * 检索字段注解{@link SearchModule}切面通知配置
     *
     * @param searchModuleAnnotationMethodInterceptor 检索字段方法拦截器{@link SearchModuleAnnotationMethodInterceptor}
     * @return {@link SearchModuleAnnotationPointcutAdvisor}
     */
    @Bean
    public SearchModuleAnnotationPointcutAdvisor searchModuleAnnotationPointcutAdvisor(SearchModuleAnnotationMethodInterceptor searchModuleAnnotationMethodInterceptor) {
        return new SearchModuleAnnotationPointcutAdvisor(searchModuleAnnotationMethodInterceptor);
    }
}
