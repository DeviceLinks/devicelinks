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

import cn.devicelinks.component.operate.log.*;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.console.authorization.UserDetailsContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
     * 创建{@link OperatorIdsProvider}对象实例
     * <p>
     * 提供给操作日志组件使用操作人相关的数据
     *
     * @return {@link OperatorIdsProvider}
     */
    @Bean
    @ConditionalOnMissingBean
    public OperatorIdsProvider operatorIdsProvider() {
        return new OperatorIdsProvider() {
            @Override
            public String getUserId() {
                return UserDetailsContext.getUserId();
            }

            @Override
            public String getSessionId() {
                return UserDetailsContext.getSessionId();
            }

            @Override
            public String getDepartmentId() {
                return UserDetailsContext.getCurrentUser().getDepartmentId();
            }
        };
    }

    /**
     * 操作日志注解{@link OperationLog}方法拦截器
     *
     * @param storageObjectProvider 日志存储 {@link OperationLogStorage}
     * @param operatorIdsProvider   操作人IDs信息提供者 {@link OperatorIdsProvider}
     * @return {@link OperationLogAnnotationMethodInterceptor}
     */
    @Bean
    public OperationLogAnnotationMethodInterceptor operationLogAnnotationMethodInterceptor(ObjectProvider<OperationLogStorage> storageObjectProvider,
                                                                                           OperatorIdsProvider operatorIdsProvider) {
        return new OperationLogAnnotationMethodInterceptor(storageObjectProvider.getIfAvailable(), operatorIdsProvider);
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
