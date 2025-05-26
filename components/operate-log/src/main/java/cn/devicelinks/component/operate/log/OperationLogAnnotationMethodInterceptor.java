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

package cn.devicelinks.component.operate.log;

import cn.devicelinks.component.operate.log.annotation.AdditionalData;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.component.operate.log.expression.ExpressionEvaluationContext;
import cn.devicelinks.component.operate.log.expression.ExpressionVariables;
import cn.devicelinks.component.operate.log.expression.OperationLogCachedExpressionEvaluator;
import cn.devicelinks.common.LogAction;
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
import java.util.List;
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
     * 定义方法是否执行成功在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String EXECUTION_SUCCEED_VARIABLE_KEY = "executionSucceed";
    /**
     * 定义操作对象在目标方法执行之前的值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String BEFORE_VARIABLE_KEY = "before";
    /**
     * 定义操作对象在目标方法执行之后的值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String AFTER_VARIABLE_KEY = "after";
    /**
     * 定义附加字段前置加载值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String ADDITIONAL_DATA_VALUE_VARIABLE_KEY = "addition_%s";

    private final OperationLogStorage operationLogStorage;

    private final OperatorIdsProvider operatorIdsProvider;

    private BeanFactoryResolver beanFactoryResolver;

    public OperationLogAnnotationMethodInterceptor(OperationLogStorage operationLogStorage, OperatorIdsProvider operatorIdsProvider) {
        this.operationLogStorage = operationLogStorage;
        this.operatorIdsProvider = operatorIdsProvider;
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
        String failureReason = null;
        Throwable failureCause = null;
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
            if (!extractor.isBatch()) {
                // Load all additional data at target method invoke before
                this.loadAdditionalData(evaluator, evaluationContext, extractor.getPreAdditionDataList(), AdditionalDataLoadTime.OperationBefore);
                try {
                    // get object before value
                    if (!ObjectUtils.isEmpty(extractor.getObjectTemplate()) &&
                            (LogAction.Update == extractor.getAction() || LogAction.Delete == extractor.getAction())) {
                        targetBeforeObject = evaluator.parseExpression(evaluationContext, Object.class, extractor.getObjectTemplate());
                        if (targetBeforeObject != null) {
                            evaluationContext.addVariable(BEFORE_VARIABLE_KEY, targetBeforeObject);
                        } else {
                            log.error("[操作日志], 当前操作为[{}], 并未获取到操作之前的对象详情, 无法存储操作日志.", extractor.getAction());
                        }
                    }
                } catch (Exception e) {
                    log.error("[操作日志], 获取操作之前对象详情遇到异常.", e);
                }
            }
            // invoke target method
            result = invocation.proceed();
            // #result
            if (result != null) {
                evaluationContext.addVariable(RESULT_VARIABLE_KEY, result);
            }
            if (!extractor.isBatch()) {
                try {
                    // Load all additional data at target method invoke after
                    this.loadAdditionalData(evaluator, evaluationContext, extractor.getPreAdditionDataList(), AdditionalDataLoadTime.OperationAfter);
                    // get object after value
                    if (!ObjectUtils.isEmpty(extractor.getObjectTemplate()) && LogAction.Update == extractor.getAction()) {
                        targetAfterObject = evaluator.parseExpression(evaluationContext, Object.class, extractor.getObjectTemplate());
                        if (targetAfterObject != null) {
                            evaluationContext.addVariable(AFTER_VARIABLE_KEY, targetAfterObject);
                        } else {
                            log.error("[操作日志], 当前操作为[{}], 并未获取到操作之后的对象详情, 无法存储操作日志.", extractor.getAction());
                        }
                    }
                } catch (Exception e) {
                    log.error("[操作日志], 获取操作之后对象详情遇到异常.", e);
                }
            }
            return result;
        } catch (Exception e) {
            failureCause = e;
            failureReason = e.getMessage();
            executionSucceed = false;
            throw e;
        } finally {
            // storage operate log
            try {
                if (evaluationContext != null) {
                    evaluationContext.addVariable(EXECUTION_SUCCEED_VARIABLE_KEY, executionSucceed);
                }
                // Conditions for recording operation logs
                boolean condition = true;
                if (!ObjectUtils.isEmpty(extractor.getConditionTemplate())) {
                    condition = evaluator.parseExpression(evaluationContext, Boolean.class, extractor.getConditionTemplate());
                }
                if (condition) {
                    if (!extractor.isBatch()) {
                        OperationLogResolveProcessor resolveProcessor =
                                new OperationLogResolveProcessor(extractor, evaluator, evaluationContext, executionSucceed, targetBeforeObject, targetAfterObject);
                        OperationLogObject operationLogObject = resolveProcessor.processing();
                        operationLogObject.setFailureReason(failureReason);
                        operationLogObject.setFailureCause(failureCause);
                        this.setOperatorInfos(operationLogObject);
                        if (this.operationLogStorage != null) {
                            this.operationLogStorage.storage(operationLogObject);
                        } else {
                            log.error("未找到[OperationLogStorage]实现类实例, 跳过存储操作日志.");
                        }
                    }
                    // Batch Operation
                    else {
                        // Save OperationLogRecorder Logs
                        List<OperationLogObject> operationLogObjects = OperationLogRecorder.getLocalOperationLogList();
                        if (!ObjectUtils.isEmpty(operationLogObjects)) {
                            ExpressionEvaluationContext finalEvaluationContext = evaluationContext;
                            operationLogObjects.forEach(logObject -> {
                                finalEvaluationContext.addVariable(EXECUTION_SUCCEED_VARIABLE_KEY, logObject.isExecutionSucceed());
                                this.setOperatorInfos(logObject);
                                logObject.setAction(extractor.getAction())
                                        .setObjectType(extractor.getObjectType());
                                if (ObjectUtils.isEmpty(logObject.getMsg()) && !ObjectUtils.isEmpty(extractor.getMsgTemplate())) {
                                    logObject.setMsg(evaluator.parseExpression(finalEvaluationContext, String.class, extractor.getMsgTemplate()));
                                }
                                if (this.operationLogStorage != null) {
                                    this.operationLogStorage.storage(logObject);
                                } else {
                                    log.error("未找到[OperationLogStorage]实现类实例, 跳过批量存储操作日志.");
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                log.error("[操作日志], 内容解析或存储操作日志时遇到异常.", e);
            } finally {
                // Clear OperationLogRecorder Logs
                OperationLogRecorder.clear();
            }
        }
    }

    private void setOperatorInfos(OperationLogObject operationLogObject) {
        if (this.operatorIdsProvider != null) {
            if (!ObjectUtils.isEmpty(this.operatorIdsProvider.getUserId())) {
                operationLogObject.setOperatorId(this.operatorIdsProvider.getUserId());
            }
            if (!ObjectUtils.isEmpty(this.operatorIdsProvider.getSessionId())) {
                operationLogObject.setSessionId(this.operatorIdsProvider.getSessionId());
            }
        }
    }

    private void loadAdditionalData(OperationLogCachedExpressionEvaluator evaluator,
                                    ExpressionEvaluationContext evaluationContext,
                                    List<AdditionalData> additionalDataList,
                                    AdditionalDataLoadTime loadTime) {
        if (ObjectUtils.isEmpty(additionalDataList)) {
            return;
        }
        additionalDataList.forEach(additionalData -> {
            try {
                if (loadTime != additionalData.loadTime() && AdditionalDataLoadTime.All != additionalData.loadTime()) {
                    return;
                }
                if (!ObjectUtils.isEmpty(additionalData.condition())) {
                    Boolean condition = evaluator.parseExpression(evaluationContext, Boolean.class, additionalData.condition());
                    if (!condition) {
                        return;
                    }
                } else {
                    return;
                }
                if (!ObjectUtils.isEmpty(additionalData.data())) {
                    String variableKey = String.format(ADDITIONAL_DATA_VALUE_VARIABLE_KEY, additionalData.key());
                    Object data = evaluator.parseExpression(evaluationContext, Object.class, additionalData.data());
                    if (data != null) {
                        evaluationContext.addVariable(variableKey, data);
                    }
                }
            } catch (Exception e) {
                log.error("[操作日志], 附加字段前置加载值解析时遇到异常.", e);
            }
        });
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!ObjectUtils.isEmpty(beanFactory) && ObjectUtils.isEmpty(this.beanFactoryResolver)) {
            this.beanFactoryResolver = new BeanFactoryResolver(beanFactory);
        }
    }
}
