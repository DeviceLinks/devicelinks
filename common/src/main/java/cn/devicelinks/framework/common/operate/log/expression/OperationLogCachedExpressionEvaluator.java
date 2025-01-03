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

package cn.devicelinks.framework.common.operate.log.expression;

import lombok.Getter;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作日志表达式评估器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class OperationLogCachedExpressionEvaluator extends CachedExpressionEvaluator {
    private static final Map<ExpressionKey, Expression> EXPRESSION_MAP = new ConcurrentHashMap<>();
    /**
     * 表达式前缀字符串
     */
    private static final String EXPRESSION_PREFIX = "{";
    /**
     * 表达式后缀字符串
     * <p>
     * SpEL仅解析前缀后缀字符串内的内容
     */
    private static final String EXPRESSION_SUFFIX = "}";

    private final AnnotatedElementKey elementKey;

    public OperationLogCachedExpressionEvaluator(AnnotatedElementKey elementKey) {
        this.elementKey = elementKey;
    }

    /**
     * 解析SpEL表达式模板
     * <p>
     * 由于expression的内容并非全部解析，所以需要重写该方法使用模板解析的方式来配置仅解析"{}"内的内容
     *
     * @param expression 表达式模板
     * @return 解析后的 {@link Expression}实例
     */
    @Override
    protected Expression parseExpression(String expression) {
        return getParser().parseExpression(expression, new TemplateParserContext(EXPRESSION_PREFIX, EXPRESSION_SUFFIX));
    }

    /**
     * 解析表达式
     *
     * @param evaluationContext  解析表达式所需要的上下文对象实例
     * @param resultType         解析后返回值的类型
     * @param expressionTemplate 等待解析的表达式
     * @return 解析后的字符串
     */
    public <T> T parseExpression(ExpressionEvaluationContext evaluationContext, Class<T> resultType, String expressionTemplate) {
        Expression expression = getExpression(EXPRESSION_MAP, this.elementKey, expressionTemplate);
        return expression.getValue(evaluationContext, resultType);
    }
}
