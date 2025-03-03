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

package cn.devicelinks.framework.common.operate.log;

import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.Variables;
import lombok.Getter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 操作日志注解提取器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class OperationLogAnnotationExtractor {
    /**
     * 定义方法参数索引值在{@link Variables}变量集合的格式
     */
    public static final String PARAMETER_INDEX_VALUE_FORMAT = "p%d";
    private final Class<?> targetClass;
    private final Method specificMethod;
    private String conditionTemplate;
    private LogAction action;
    private LogObjectType objectType;
    private String objectIdTemplate;
    private String objectTemplate;
    private String msgTemplate;
    private String activateDataTemplate;
    private Object[] arguments;
    private Parameter[] parameters;
    private final List<AdditionalData> preAdditionDataList = new ArrayList<>();

    public OperationLogAnnotationExtractor(MethodInvocation invocation) {
        this.targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        this.specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        OperationLog operationLog = AnnotationUtils.getAnnotation(invocation.getMethod(), OperationLog.class);
        if (operationLog != null) {
            this.conditionTemplate = operationLog.condition();
            this.action = operationLog.action();
            this.objectType = operationLog.objectType();
            this.objectIdTemplate = operationLog.objectId();
            this.objectTemplate = operationLog.object();
            this.activateDataTemplate = operationLog.activateData();
            this.msgTemplate = operationLog.msg();
            this.arguments = invocation.getArguments();
            this.parameters = this.specificMethod.getParameters();

            AdditionalData[] additionFields = operationLog.additional();
            if (!ObjectUtils.isEmpty(additionFields)) {
                this.preAdditionDataList.addAll(Arrays.asList(additionFields));
            }
        }
    }

    /**
     * 获取参数定义以及值的映射集合
     * 通过{@link MethodInvocation}实例获取切面目标的{@link Method}，通过{@link MethodInvocation#getArguments()}获取各个参数的传递值
     *
     * @return 定义参数的值集合
     */
    public Map<String, Object> getParameterValues() {
        Map<String, Object> parameterValues = new HashMap<>();
        if (ObjectUtils.isEmpty(parameters)) {
            return parameterValues;
        }
        for (int i = 0; i < parameters.length; i++) {
            Object value = this.arguments[i];
            String parameterIndexName = String.format(PARAMETER_INDEX_VALUE_FORMAT, i);
            parameterValues.put(parameterIndexName, value);
        }
        return parameterValues;
    }
}
