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

import cn.devicelinks.framework.common.OperateAction;
import cn.devicelinks.framework.common.OperateObjectType;
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
     * {@link OperateObjectType#User}
     *
     * @see cn.devicelinks.framework.common.pojos.SysUser
     */
    user_id(OperateObjectType.User, "id", "用户ID", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    user_name(OperateObjectType.User, "name", "用户名称", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    user_account(OperateObjectType.User, "account", "用户账号", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    user_department(OperateObjectType.User, "department", "部门", OperateAction.Add, OperateAction.Update),
    user_position(OperateObjectType.User, "position", "岗位", OperateAction.Add, OperateAction.Update),
    /**
     * {@link OperateObjectType#Department}
     *
     * @see cn.devicelinks.framework.common.pojos.SysDepartment
     */
    department_id(OperateObjectType.Department, "id", "部门ID", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    department_name(OperateObjectType.Department, "name", "部门名称", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    department_code(OperateObjectType.Department, "code", "部门编码", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    department_pid(OperateObjectType.Department, "pid", "上级部门ID", OperateAction.Add, OperateAction.Update, OperateAction.Delete),
    department_level(OperateObjectType.Department, "level", "部门等级", OperateAction.Add, OperateAction.Update),
    department_sort(OperateObjectType.Department, "sort", "部门排序", OperateAction.Add, OperateAction.Update),
    ;

    private final OperateObjectType objectType;
    private final String field;
    private final String fieldName;
    private final List<OperateAction> matchActions;

    ObjectField(OperateObjectType objectType, String field, String fieldName, OperateAction... matchActions) {
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
    public static List<ObjectField> getFields(OperateObjectType objectType, OperateAction operateAction) {
        // @formatter:off
        return Arrays.stream(values())
                .filter(fs -> fs.getObjectType() == objectType)
                .filter(fs -> fs.getMatchActions().contains(operateAction))
                .collect(Collectors.toList());
        // @formatter:on
    }
}
