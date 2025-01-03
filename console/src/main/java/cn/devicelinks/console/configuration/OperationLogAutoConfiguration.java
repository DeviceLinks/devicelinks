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

import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.operate.log.OperationLogAnnotationMethodInterceptor;
import cn.devicelinks.framework.common.operate.log.OperationLogAnnotationPointcutAdvisor;
import cn.devicelinks.framework.common.operate.log.OperationLogStorage;
import cn.devicelinks.framework.common.security.SecurityUserDetailsProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志自动化配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ConditionalOnClass({OperationLogAnnotationMethodInterceptor.class, OperationLogAnnotationPointcutAdvisor.class})
@Configuration
public class OperationLogAutoConfiguration {
    /**
     * 操作日志注解{@link OperationLog}方法拦截器
     *
     * @param storageObjectProvider 日志存储 {@link OperationLogStorage}
     * @param userDetailsProvider   用户详情提供者 {@link SecurityUserDetailsProvider}
     * @return {@link OperationLogAnnotationMethodInterceptor}
     */
    @Bean
    public OperationLogAnnotationMethodInterceptor operationLogAnnotationMethodInterceptor(ObjectProvider<OperationLogStorage> storageObjectProvider,
                                                                                           SecurityUserDetailsProvider userDetailsProvider) {
        return new OperationLogAnnotationMethodInterceptor(storageObjectProvider.getIfAvailable(), userDetailsProvider);
    }

    /**
     * 操作日志注解{@link OperationLog}切面通知配置
     *
     * @param operationLogMethodInterceptor 操作日志方法拦截器{@link OperationLogAnnotationMethodInterceptor}
     * @return {@link OperationLogAnnotationPointcutAdvisor}
     */
    @Bean
    public OperationLogAnnotationPointcutAdvisor operationLogAnnotationPointcutAdvisor(OperationLogAnnotationMethodInterceptor operationLogMethodInterceptor) {
        return new OperationLogAnnotationPointcutAdvisor(operationLogMethodInterceptor);
    }
}
