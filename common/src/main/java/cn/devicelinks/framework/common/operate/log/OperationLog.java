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

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.operate.log.expression.ExpressionVariables;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.common.pojos.SysUser;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * <p>
 * 操作日志支持通过SpEL表达式来解析数据，可以直接配置代码来协助解析
 * 为了方便使用操作日志，其中内置了一些变量，如下所示：
 * - "#p"：参数对象，索引从0开始；如想要使用第一个参数时为"#p0"，以此类推。
 * - "#before": 前置数据；在目标方法之前执行，支持SpEL表达式。依据{@link #object()}以及{@link LogAction#isHaveBeforeData()}
 * - "#after": 后置数据；在目标方法之后执行，支持SpEL表达式。依据{@link #object()}以及{@link LogAction#isHaveAfterData()}
 * - "#result": 目标方法返回值；目标方法执行成功之后会将方法的返回值作为整个对象设置到变量集合中，可以直接通过"{#result.xxx}"来获取，支持多层级获取
 * - "#executionSucceed"：目标方法是否执行成功；该值是boolean类型的，目标方法执行成功返回{@link Boolean#TRUE}，目标方法执行过程中遇到异常则返回{@link Boolean#FALSE}
 * - "#pre": 前置数据；用于附加字段加载前置数据，可以直接使用"{#pre.xxx}"来获取，支持多层级获取
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    /**
     * 记录操作日志的条件
     * 支持SpEL表达式
     *
     * @return 解析后的值为 {@link Boolean} 类型，当为"true"时才会存储操作日志
     */
    String condition() default "true";

    /**
     * 操作动作
     *
     * @return {@link LogAction}
     */
    LogAction action();

    /**
     * 操作对象类型
     *
     * @return {@link LogObjectType}
     */
    LogObjectType objectType();

    /**
     * 操作对象ID
     * 支持SpEL表达式，在目标方法执行后调用，可使用"#before"、"#after"、"#result"、"#pre"等内置变量
     * 目标方法执行成功或失败都会执行数据解析
     * <p>
     * 跟{@link #objectType()}一一对应。
     * 如：{@link LogObjectType}，那么操作对象可以为{@link SysUser#getAccount()}
     * 自行定义操作对象，该值会被存储到{@link SysLog#getObjectId()}数据列中
     *
     * @return 获取操作对象值的SpEL表达式，解析后获取操作对象详情中用户可读的唯一标识字段值，如：{@link SysUser#getAccount()}
     */
    String objectId();

    /**
     * 获取操作目标对象
     * <p>
     * 支持SpEL表达式
     * 仅适用于{@link LogAction#Update}、{@link LogAction#Delete}两种动作
     * <p>
     * 对于{@link LogAction#Update}会在目标方法执行之前获取操作目标对象，并将获取到的数据存入{@link ExpressionVariables}变量集合中，
     * 变量名为："#before"。目标方法执行完成后并且是执行成功状态"#executionSucceed=true"，则再次执行一次获取操作目标对象
     * 将目标对象存入{@link ExpressionVariables}变量集合中，变量名为："#after"。
     * <p>
     * 对于{@link LogAction#Delete}仅会在目标方法执行之前执行一次获取操作目标对象
     * 将目标对象存入{@link ExpressionVariables}变量集合中，变量名为："#before"
     *
     * @return 获取操作目标对象详情的SpEL表达式，一般解析后返回对象实例，如{@link SysUser}
     */
    String object() default Constants.EMPTY_STRING;

    /**
     * 日志内容
     * <p>
     * 支持SpEL表达式
     * <p>
     * 无论目标方法执行成功还是失败都会解析，可以通过"{#executionSucceed}"来判断是否执行成功
     *
     * @return 操作描述SpEL表达式，解析后为本次操作的文字描述
     */
    String msg() default Constants.EMPTY_STRING;

    /**
     * 附加数据
     * <p>
     * 支持SpEL表达式
     * <p>
     * 将每个{@link AdditionalData}加载到的数据根据顺序依次存入{@link ExpressionVariables}变量集合中，
     * 可通过"{#pre{index}}"的方式调用，以便于操作日志的判断以及格式化数据内容，变量名依次为（索引从0开始）：#pre0、#pre1、...
     *
     * @return {@link AdditionalData}
     */
    AdditionalData[] additional() default {};

    /**
     * 当前操作的活动数据
     * <p>
     * 会将解析后的值解析成JSON字符串存储到{@link SysLog#getActivityData()}
     * 目标方法执行成功或失败都会执行数据解析
     * <p>
     * 支持SpEL表达式，一般配置请求的数据内容，也可以配置操作后的数据内容。
     * 在目标方法执行后调用，可使用"#before"、"#after"、"#result"、"#pre"等内置变量
     *
     * @return 获取当前操作活动数据SpEL表达式
     */
    String activateData();
}
