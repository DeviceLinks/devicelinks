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
import cn.devicelinks.framework.common.request.RequestContext;
import cn.devicelinks.framework.common.request.RequestContextHolder;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.utils.JacksonUtils;
import cn.devicelinks.framework.common.utils.ObjectClassUtils;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 操作日志内容解析处理类
 * <p>
 * 解析{@link OperationLog}操作日志注解所提供的配置内容，封装成{@link OperationLogObject}日志对象实例交付给后续"processor"
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class OperationLogResolveProcessor {
    /**
     * 定义目标操作对象在{@link ExpressionVariables}变量集合内的Key
     * 注意：如果在使用"#before"获取字段值时，"#target = #before"，如果在使用"#after"获取字段值时，"#target = #after"
     */
    private static final String TARGET_VARIABLE_KEY = "target";
    /**
     * 定义附加字段前置加载值在{@link ExpressionVariables}变量集合内的Key
     */
    private static final String ADDITIONAL_FIELD_PRE_VALUE_VARIABLE_KEY = "pre%d";
    private OperationLogObject operationLogObject;
    private OperationLogAnnotationExtractor extractor;
    private OperationLogCachedExpressionEvaluator evaluator;
    private ExpressionEvaluationContext evaluationContext;
    private boolean executionSucceed;
    private Object beforeObject;
    private Object afterObject;

    public OperationLogResolveProcessor(OperationLogAnnotationExtractor extractor, OperationLogCachedExpressionEvaluator evaluator,
                                        ExpressionEvaluationContext evaluationContext,
                                        boolean executionSucceed, Object beforeObject, Object afterObject) {
        this.extractor = extractor;
        this.operationLogObject = new OperationLogObject();
        this.operationLogObject.setExecutionSucceed(executionSucceed);
        this.evaluator = evaluator;
        this.evaluationContext = evaluationContext;
        this.executionSucceed = executionSucceed;
        this.beforeObject = beforeObject;
        this.afterObject = afterObject;
    }

    public OperationLogObject processing() {
        // @formatter:off
        if (!ObjectUtils.isEmpty(extractor.getObjectIdTemplate())) {
            this.operationLogObject.setObjectId(this.executionSucceed ?
                    evaluator.parseExpression(this.evaluationContext, String.class, extractor.getObjectIdTemplate()) : null);
        }
        if (!ObjectUtils.isEmpty(extractor.getActivateDataTemplate())) {
            Object activateData = evaluator.parseExpression(this.evaluationContext, Object.class, extractor.getActivateDataTemplate());
            this.operationLogObject.setActivateData(this.executionSucceed ?
                    !ObjectUtils.isEmpty(activateData) ? JacksonUtils.objectToJson(activateData) : null
                    : null);
        }
        if (!ObjectUtils.isEmpty(extractor.getMsgTemplate())) {
            this.operationLogObject.setMsg(this.executionSucceed ?
                    evaluator.parseExpression(this.evaluationContext, String.class, extractor.getMsgTemplate()) : null);
        }
        RequestContext requestContext = RequestContextHolder.getContext();
        if (requestContext != null) {
            this.operationLogObject.setIpAddress(requestContext.getIp());
        }
        List<ObjectField> objectFields = this.extractor.getObjectFieldMap().values().stream().toList();
        if (!ObjectUtils.isEmpty(objectFields)) {
            // before object field value list
            List<ObjectFieldValue> beforeObjectFieldValueList = this.mapObjectFieldValue(objectFields, this.beforeObject);
            // after object field value list
            List<ObjectFieldValue> afterObjectFieldValueList = this.mapObjectFieldValue(objectFields, this.afterObject);
            // field different value list
            List<ObjectFieldDifferentValue> fieldDifferentValueList = this.getValueDifferentFields(objectFields, beforeObjectFieldValueList, afterObjectFieldValueList);
            this.operationLogObject.setObjectFields(JacksonUtils.objectToJson(fieldDifferentValueList));
        }
        this.operationLogObject
                .setAction(extractor.getAction())
                .setObjectType(extractor.getObjectType());
        // @formatter:on
        return this.operationLogObject;
    }

    private List<ObjectFieldValue> mapObjectFieldValue(List<ObjectField> objectFields, Object targetObject) {
        // @formatter:off
        Map<String, Object> targetObjectClassFieldValueMap = Maps.newHashMap();
        if (targetObject != null) {
            Field[] targetObjectClassFields = ObjectClassUtils.getClassFields(targetObject.getClass());
            Arrays.stream(targetObjectClassFields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object fieldValue = field.get(targetObject);
                            targetObjectClassFieldValueMap.put(field.getName(), fieldValue);
                        } catch (Exception e) {
                            log.error("[操作日志]获取目标对象[" + targetObject.getClass().getName() + "]字段值时遇到异常.", e.getMessage());
                        }
                    });
        }
        this.evaluationContext.addVariable(TARGET_VARIABLE_KEY, targetObject);
        return objectFields.stream()
                .map(objectField -> {
                    Object fieldValue = this.getFieldValue(objectField, targetObjectClassFieldValueMap);
                    return new ObjectFieldValue(objectField.getField(), objectField.getFieldName(), fieldValue);
                }).toList();
        // @formatter:on
    }

    private Object getFieldValue(ObjectField objectField, Map<String, Object> classFieldValueMap) {
        Object fieldValue = null;
        if (this.extractor.getAdditionFieldMap().containsKey(objectField.getField())) {
            ObjectAdditionField objectAdditionField = this.extractor.getAdditionFieldMap().get(objectField.getField());
            if (ObjectUtils.isEmpty(objectAdditionField.condition()) || ObjectUtils.isEmpty(objectAdditionField.value())) {
                log.error("[ObjectAdditionField][{}], condition() or value() is empty and the value cannot be obtained through SpEL.", objectAdditionField.field());
                return fieldValue;
            }
            boolean condition = evaluator.parseExpression(this.evaluationContext, Boolean.class, objectAdditionField.condition());
            // Get the value after the condition is met
            if (condition) {
                // load pre values
                String[] preValueSpELArray = objectAdditionField.preValueLoad();
                if (!ObjectUtils.isEmpty(preValueSpELArray)) {
                    IntStream.range(Constants.ZERO, preValueSpELArray.length).forEach(index -> {
                        String preValueSpEL = preValueSpELArray[index];
                        if (!ObjectUtils.isEmpty(preValueSpEL)) {
                            Object preValue = evaluator.parseExpression(this.evaluationContext, Object.class, preValueSpEL);
                            if (preValue != null) {
                                this.evaluationContext.addVariable(String.format(ADDITIONAL_FIELD_PRE_VALUE_VARIABLE_KEY, index), preValue);
                            } else {
                                log.error("[ObjectAdditionField][{}], Pre value loading failed, cannot use #pre variable. ", objectAdditionField.field());
                            }
                        }
                    });
                }
                // set field value
                String fieldValueSpEL = objectAdditionField.value();
                if (!ObjectUtils.isEmpty(fieldValueSpEL)) {
                    fieldValue = evaluator.parseExpression(this.evaluationContext, Object.class, fieldValueSpEL);
                }
            }
        } else {
            return classFieldValueMap.get(objectField.getField());
        }
        return fieldValue;
    }

    private List<ObjectFieldDifferentValue> getValueDifferentFields(List<ObjectField> objectFields, List<ObjectFieldValue> beforeFieldValues, List<ObjectFieldValue> afterFieldValues) {
        // @formatter:off
        // before field value map
        Map<String, Object> beforeFieldValueMap = !ObjectUtils.isEmpty(beforeFieldValues) ? beforeFieldValues.stream()
                .filter(f -> f.getValue() != null)
                .collect(Collectors.toMap(ObjectFieldValue::getField, ObjectFieldValue::getValue)) : Maps.newHashMap();
        // after field value map
        Map<String, Object> afterFieldValueMap = !ObjectUtils.isEmpty(afterFieldValues) ? afterFieldValues.stream()
                .filter(f -> f.getValue() != null)
                .collect(Collectors.toMap(ObjectFieldValue::getField, ObjectFieldValue::getValue)) : Maps.newHashMap();
        return objectFields.stream()
                .map(field -> {
                    Object beforeValue = beforeFieldValueMap.get(field.getField());
                    Object afterValue = afterFieldValueMap.get(field.getField());
                    boolean different = (beforeValue != null && !beforeValue.equals(afterFieldValueMap.get(field.getField()))) ||
                            (afterValue != null && !afterValue.equals(beforeFieldValueMap.get(field.getField())));
                    return new ObjectFieldDifferentValue(field.getField(), field.getFieldName(), beforeValue, afterValue, different);
                }).toList();
        // @formatter:on
    }
}
