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

import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作对象字段定义枚举
 * <p>
 * 该枚举定义的字段提取值时，首先先从目标对象中获取，然后再从附加字段中获取
 * 如果附加字段重复添加了相同的字段，则会覆盖从目标对象中获取的值
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum ObjectField {
    /**
     * {@link LogObjectType#User}
     *
     * @see cn.devicelinks.framework.common.pojos.SysUser
     */
    user_id(LogObjectType.User, "id", "用户ID", LogAction.Add, LogAction.Update, LogAction.Delete),
    user_name(LogObjectType.User, "name", "用户名称", LogAction.Add, LogAction.Update, LogAction.Delete),
    user_account(LogObjectType.User, "account", "用户账号", LogAction.Add, LogAction.Update, LogAction.Delete),
    user_email(LogObjectType.User, "email", "邮箱地址", LogAction.Add, LogAction.Update, LogAction.Delete),
    user_phone(LogObjectType.User, "phone", "手机号码", LogAction.Add, LogAction.Update, LogAction.Delete),
    user_department(LogObjectType.User, "department", "部门", LogAction.Add, LogAction.Update),
    /**
     * {@link LogObjectType#Department}
     *
     * @see cn.devicelinks.framework.common.pojos.SysDepartment
     */
    department_id(LogObjectType.Department, "id", "部门ID", LogAction.Add, LogAction.Update, LogAction.Delete),
    department_name(LogObjectType.Department, "name", "部门名称", LogAction.Add, LogAction.Update, LogAction.Delete),
    department_code(LogObjectType.Department, "code", "部门编码", LogAction.Add, LogAction.Update, LogAction.Delete),
    department_pid(LogObjectType.Department, "pid", "上级部门ID", LogAction.Add, LogAction.Update, LogAction.Delete),
    department_level(LogObjectType.Department, "level", "部门等级", LogAction.Add, LogAction.Update),
    department_sort(LogObjectType.Department, "sort", "部门排序", LogAction.Add, LogAction.Update),
    ;

    private final LogObjectType objectType;
    private final String field;
    private final String fieldName;
    private final List<LogAction> matchActions;

    ObjectField(LogObjectType objectType, String field, String fieldName, LogAction... matchActions) {
        this.field = field;
        this.fieldName = fieldName;
        this.objectType = objectType;
        this.matchActions = List.of(matchActions);
    }

    /**
     * 获取指定操作对象类指定操作动作的操作日志字段列表
     *
     * @param objectType    操作对象类型
     * @param operateAction 操作动作
     * @return {@link ObjectField}
     */
    public static List<ObjectField> getFields(LogObjectType objectType, LogAction operateAction) {
        // @formatter:off
        return Arrays.stream(values())
                .filter(fs -> fs.getObjectType() == objectType)
                .filter(fs -> fs.getMatchActions().contains(operateAction))
                .collect(Collectors.toList());
        // @formatter:on
    }
}
