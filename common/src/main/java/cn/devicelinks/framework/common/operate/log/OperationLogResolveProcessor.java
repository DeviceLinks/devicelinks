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

import cn.devicelinks.framework.common.operate.log.expression.ExpressionEvaluationContext;
import cn.devicelinks.framework.common.operate.log.expression.OperationLogCachedExpressionEvaluator;
import cn.devicelinks.framework.common.web.RequestContext;
import cn.devicelinks.framework.common.web.RequestContextHolder;
import cn.devicelinks.framework.common.utils.HttpRequestUtils;
import cn.devicelinks.framework.common.utils.JacksonUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

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
    private OperationLogObject operationLogObject;
    private OperationLogAnnotationExtractor extractor;
    private OperationLogCachedExpressionEvaluator evaluator;
    private ExpressionEvaluationContext evaluationContext;
    private Object beforeObject;
    private Object afterObject;

    public OperationLogResolveProcessor(OperationLogAnnotationExtractor extractor, OperationLogCachedExpressionEvaluator evaluator,
                                        ExpressionEvaluationContext evaluationContext, boolean executionSucceed, Object beforeObject, Object afterObject) {
        this.extractor = extractor;
        this.operationLogObject = new OperationLogObject();
        this.operationLogObject.setExecutionSucceed(executionSucceed);
        this.evaluator = evaluator;
        this.evaluationContext = evaluationContext;
        this.beforeObject = beforeObject;
        this.afterObject = afterObject;
    }

    public OperationLogObject processing() {
        // @formatter:off
        if (!ObjectUtils.isEmpty(extractor.getObjectIdTemplate())) {
            this.operationLogObject.setObjectId(evaluator.parseExpression(this.evaluationContext, String.class, extractor.getObjectIdTemplate()));
        }
        if (!ObjectUtils.isEmpty(extractor.getObjectTemplate())) {
            this.operationLogObject.setBeforeObject(this.beforeObject)
                    .setAfterObject(this.afterObject);
        }
        if (!ObjectUtils.isEmpty(extractor.getActivateDataTemplate())) {
            Object activateData = evaluator.parseExpression(this.evaluationContext, Object.class, extractor.getActivateDataTemplate());
            this.operationLogObject.setActivateData(!ObjectUtils.isEmpty(activateData) ? JacksonUtils.objectToJson(activateData) : null);
        }
        if (!ObjectUtils.isEmpty(extractor.getMsgTemplate())) {
            this.operationLogObject.setMsg(evaluator.parseExpression(this.evaluationContext, String.class, extractor.getMsgTemplate()));
        }
        RequestContext requestContext = RequestContextHolder.getContext();
        if (requestContext != null) {
            this.operationLogObject.setIpAddress(requestContext.getIp());
            this.operationLogObject.setOs(HttpRequestUtils.getOS(requestContext.getRequest()));
            this.operationLogObject.setBrowser(HttpRequestUtils.getBrowser(requestContext.getRequest()));
        }
        this.operationLogObject
                .setAction(extractor.getAction())
                .setObjectType(extractor.getObjectType());
        // @formatter:on
        return this.operationLogObject;
    }
}
