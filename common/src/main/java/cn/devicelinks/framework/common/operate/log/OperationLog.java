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

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.operate.log.expression.ExpressionVariables;
import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.common.pojos.SysLogAddition;
import cn.devicelinks.framework.common.pojos.SysUser;

import java.lang.annotation.*;

/**
 * 操作日志注解
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
     * @return 解析后的值为 {@link Boolean} 类型，当为"true"时才会执行操作日志记录逻辑
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
     * 配置用于获取“对象详细信息”的表达式
     * 支持SpEL表达式
     * <p>
     * 获取到非空值后会根据{@link LogObjectType}以及{@link LogAction}提取到本次操作的{@link ObjectField}对象字段列表，
     * 将对象字段列表与对象详细信息实例进行匹配，将匹配到的字段值以JSON字符串的形式存储到{@link SysLogAddition#getObjectFields()}数据列中
     * <p>
     * 在操作目标方法之前将获取到非空值存入{@link ExpressionVariables}变量集合中，变量名为"before"，可以通过"{#before.xxx}"进行调用，如："{#before.account}"、"{#before.getLastName()}"
     * 在操作目标方法之后将获取到非空值存入{@link ExpressionVariables}变量集合中，变量名为"after"，可以通过"{#before.xxx}"进行调用，如："{#after.account}"、"{#after.getLastName()}"
     *
     * @return 获取操作目标对象详情的SpEL表达式，一般解析后返回对象实例，如{@link SysUser}
     */
    String object() default Constants.EMPTY_STRING;

    /**
     * 操作描述
     * 支持SpEL表达式
     * <p>
     * 只有在目标方法执行成功后解析
     *
     * @return 操作描述SpEL表达式，解析后为本次操作的文字描述
     */
    String msg() default "";

    /**
     * 附加字段列表
     * <p>
     * 配置操作对象详情中不存在的额外附加字段列表
     * <p>
     * 如：想记录用户的部门名称，而{@link SysUser}对象中并不存在”部门名称“{@link SysDepartment#getName()}字段，只有”部门ID“{@link SysUser#getDepartmentId()} ()}字段
     * 这时我们就可以通过配置附加字段来提取到”部门名称“的值并存储到{@link SysLogAddition#getObjectFields()}进行记录存在
     * <p>
     * 附加字段都在{@link ObjectField}中与标准字段一并定义
     *
     * @return {@link ObjectAdditionField}
     */
    ObjectAdditionField[] additionFields() default {};

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
