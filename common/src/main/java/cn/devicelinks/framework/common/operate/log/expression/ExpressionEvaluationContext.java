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

package cn.devicelinks.framework.common.operate.log.expression;

import lombok.Getter;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

/**
 * 用于解析SpEL表达式的上下文实例
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ExpressionEvaluationContext extends StandardEvaluationContext {
    private final ExpressionVariables expressionVariables;

    public ExpressionEvaluationContext(ExpressionVariables variables, BeanFactoryResolver beanFactoryResolver) {
        Assert.notNull(variables, "variables must not be null");
        Assert.notNull(beanFactoryResolver, "beanFactoryResolver must not be null");
        this.expressionVariables = variables;
        this.setVariables(variables.getAllVariables());
        this.setBeanResolver(beanFactoryResolver);
    }

    public void addVariable(String key, Object value) {
        this.getExpressionVariables().setVariable(key, value);
        this.setVariables(this.getExpressionVariables().getAllVariables());
    }
}
