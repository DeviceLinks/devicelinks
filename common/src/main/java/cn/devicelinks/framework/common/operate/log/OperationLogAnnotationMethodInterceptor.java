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

package cn.devicelinks.framework.common.operate.log;

import cn.devicelinks.framework.common.operate.log.expression.ExpressionEvaluationContext;
import cn.devicelinks.framework.common.operate.log.expression.ExpressionVariables;
import cn.devicelinks.framework.common.operate.log.expression.OperationLogCachedExpressionEvaluator;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.common.security.SecurityUserDetailsProvider;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * {@link OperationLog}操作日志注解方法拦截器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class OperationLogAnnotationMethodInterceptor implements MethodInterceptor, BeanFactoryAware {
    /**
     * 定义方法返回值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String RESULT_VARIABLE_KEY = "result";
    /**
     * 定义操作对象在目标方法执行之前的值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String BEFORE_VARIABLE_KEY = "before";
    /**
     * 定义操作对象在目标方法执行之后的值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String AFTER_VARIABLE_KEY = "after";
    private final OperationLogStorage operationLogStorage;
    private final SecurityUserDetailsProvider userDetailsProvider;
    private BeanFactoryResolver beanFactoryResolver;

    public OperationLogAnnotationMethodInterceptor(OperationLogStorage operationLogStorage, SecurityUserDetailsProvider userDetailsProvider) {
        this.operationLogStorage = operationLogStorage;
        this.userDetailsProvider = userDetailsProvider;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        OperationLogAnnotationExtractor extractor = new OperationLogAnnotationExtractor(invocation);
        ExpressionVariables variables = new ExpressionVariables();
        AnnotatedElementKey elementKey = new AnnotatedElementKey(extractor.getSpecificMethod(), extractor.getTargetClass());
        OperationLogCachedExpressionEvaluator evaluator = new OperationLogCachedExpressionEvaluator(elementKey);
        ExpressionEvaluationContext evaluationContext = null;
        boolean executionSucceed = true;
        String exceptionMessage = null;
        // invoke method before, target object value
        Object targetBeforeObject = null;
        // invoke method after, target object value
        Object targetAfterObject = null;
        Object result;
        try {
            Map<String, Object> parameterValues = extractor.getParameterValues();
            if (!ObjectUtils.isEmpty(parameterValues)) {
                variables.addVariables(parameterValues);
            }
            evaluationContext = new ExpressionEvaluationContext(variables, beanFactoryResolver);
            // Conditions for recording operation logs
            boolean condition = evaluator.parseExpression(evaluationContext, Boolean.class, extractor.getConditionTemplate());
            try {
                // get object before value
                if (condition && !ObjectUtils.isEmpty(extractor.getObjectTemplate()) && extractor.getAction().isHaveBeforeData()) {
                    targetBeforeObject = evaluator.parseExpression(evaluationContext, Object.class, extractor.getObjectTemplate());
                    if (targetBeforeObject != null) {
                        evaluationContext.addVariable(BEFORE_VARIABLE_KEY, targetBeforeObject);
                    }
                    if (targetBeforeObject == null && extractor.getAction().isHaveBeforeData()) {
                        log.error("[操作日志], 当前操作为[{}], 并未获取到操作之前的对象详情, 无法存储操作日志.", extractor.getAction());
                    }
                }
            } catch (Exception e) {
                log.error("[操作日志], 获取操作之前对象详情遇到异常.", e);
            }
            // invoke target method
            result = invocation.proceed();
            if (!condition) {
                return result;
            } else {
                // #result
                if (result != null) {
                    evaluationContext.addVariable(RESULT_VARIABLE_KEY, result);
                }
            }
            try {
                // get object after value
                if (!ObjectUtils.isEmpty(extractor.getObjectTemplate()) && extractor.getAction().isHaveAfterData()) {
                    targetAfterObject = evaluator.parseExpression(evaluationContext, Object.class, extractor.getObjectTemplate());
                    if (targetAfterObject != null) {
                        evaluationContext.addVariable(AFTER_VARIABLE_KEY, targetAfterObject);
                    }
                    if (targetAfterObject == null && extractor.getAction().isHaveAfterData()) {
                        log.error("[操作日志], 当前操作为[{}], 并未获取到操作之后的对象详情, 无法存储操作日志.", extractor.getAction());
                    }
                }
            } catch (Exception e) {
                log.error("[操作日志], 获取操作之后对象详情遇到异常.", e);
            }
            return result;
        } catch (Exception e) {
            exceptionMessage = e.getMessage();
            throw e;
        } finally {
            // storage operate log
            try {
                OperationLogResolveProcessor resolveProcessor =
                        new OperationLogResolveProcessor(extractor, evaluator, evaluationContext, executionSucceed, targetBeforeObject, targetAfterObject);
                OperationLogObject operationLogObject = resolveProcessor.processing();
                operationLogObject.setFailureReason(exceptionMessage);
                if (this.userDetailsProvider != null) {
                    if (this.userDetailsProvider.getUser() != null) {
                        SysUser sysUser = this.userDetailsProvider.getUser();
                        operationLogObject.setOperatorId(sysUser.getId());
                    }
                    operationLogObject.setSessionId(this.userDetailsProvider.getSessionId());
                }
                if (this.operationLogStorage != null) {
                    this.operationLogStorage.storage(operationLogObject);
                } else {
                    log.error("未找到[OperationLogStorage]实现类实例, 跳过存储操作日志.");
                }
            } catch (Exception e) {
                log.error("[操作日志], 内容解析或存储操作日志时遇到异常.", e);
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!ObjectUtils.isEmpty(beanFactory) && ObjectUtils.isEmpty(this.beanFactoryResolver)) {
            this.beanFactoryResolver = new BeanFactoryResolver(beanFactory);
        }
    }
}
