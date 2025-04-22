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

package cn.devicelinks.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 共享变量公共类
 * <p>
 * 支持通过分组的方式对变量进行归类
 *
 * @author 恒宇少年
 */
public class Variables implements Serializable {

    public static final String DEFAULT_GROUP = "DEFAULT";
    protected final ConcurrentMap<String, Map<String, Object>> VARIABLES = new ConcurrentHashMap<>();

    /**
     * 设置默认分组内的变量值
     *
     * @param name  变量名
     * @param value 变量的值
     */
    public void setVariable(String name, Object value) {
        setVariable(DEFAULT_GROUP, name, value);
    }

    /**
     * 设置默认分组内的变量值
     * <p>
     * 如果系统中使用枚举来定义变量名，可以通过该方法进行设置
     *
     * @param e     变量名的枚举对象
     * @param value 对应的值
     */
    public void setVariable(Enum<?> e, Object value) {
        this.setVariable(e.toString(), value);
    }

    /**
     * 设置指定分组内的变量值
     *
     * @param group 分组名称
     * @param name  变量名
     * @param value 变量值
     */
    public void setVariable(String group, String name, Object value) {
        Map<String, Object> groupVariables = Optional.ofNullable(VARIABLES.get(group)).orElse(new HashMap<>());
        groupVariables.put(name, value);
        VARIABLES.put(group, groupVariables);
    }

    /**
     * 设置指定分组内的变量值
     * <p>
     * 如果系统中使用枚举来定义变量名，可以通过该方法进行设置
     *
     * @param group 分组名称
     * @param e     变量名的枚举对象
     * @param value 变量的值
     */
    public void setVariable(String group, Enum<?> e, Object value) {
        this.setVariable(group, e.toString(), value);
    }

    /**
     * 获取默认分组内的变量值
     *
     * @param name 变量名
     * @param <T>  变量类型
     * @return 变量的值
     */
    public <T> T getVariable(String name) {
        return getVariable(DEFAULT_GROUP, name);
    }

    /**
     * 获取默认分组内的变量值
     *
     * @param e   变量名的枚举对象
     * @param <T> 变量类型
     * @return 变量的值
     */
    public <T> T getVariable(Enum<?> e) {
        return this.getVariable(e.toString());
    }

    /**
     * 获取并检查有效性默认分组内的变量值
     *
     * @param e   变量名的枚举对象
     * @param <T> 变量类型
     * @return 变量的值
     */
    public <T> T getAndCheckVariable(Enum<?> e) {
        T t = this.getVariable(e.toString());
        if (t == null) {
            throw new IllegalArgumentException("上下文中不存在变量：" + e.toString() + "，的值.");
        }
        return t;
    }

    /**
     * 获取指定分组内的变量值
     *
     * @param group 分组名称
     * @param name  变量名
     * @param <T>   变量类型
     * @return 变量的值
     */
    public <T> T getVariable(String group, String name) {
        Map<String, Object> variables = VARIABLES.get(group);
        return variables != null ? (T) variables.get(name) : null;
    }

    /**
     * 获取指定分组内的变量值
     *
     * @param group 分组名称
     * @param e     变量名的枚举对象
     * @param <T>   变量类型
     * @return 变量的值
     */
    public <T> T getVariable(String group, Enum<?> e) {
        return this.getVariable(group, e.toString());
    }

    /**
     * 删除指定分组内的变量值
     *
     * @param group 分组名称
     * @param name  变量名称
     */
    public void removeVariable(String group, String name) {
        Optional.ofNullable(VARIABLES.get(group)).ifPresent(variables -> variables.remove(name));
    }

    /**
     * 删除默认分组内的变量值
     *
     * @param name 变量名称
     */
    public void removeVariable(String name) {
        this.removeVariable(DEFAULT_GROUP, name);
    }

    /**
     * 删除默认分组集合内的变量
     *
     * @param e 变量名枚举对象
     */
    public void removeVariable(Enum<?> e) {
        this.removeVariable(e.toString());
    }

    /**
     * 合并变量集合
     * <p>
     * 将指定的变量集合合并到当前变量集合内
     *
     * @param waitMergeVariables 等待合并的集合
     */
    public void mergeVariables(Variables waitMergeVariables) {
        for (String group : waitMergeVariables.VARIABLES.keySet()) {
            Map<String, Object> waitMergeVariableMap = waitMergeVariables.VARIABLES.get(group);
            if (this.VARIABLES.containsKey(group)) {
                Map<String, Object> groupVariableMap = this.VARIABLES.get(group);
                for (String variableKey : waitMergeVariableMap.keySet()) {
                    if (!groupVariableMap.containsKey(variableKey)) {
                        groupVariableMap.put(variableKey, waitMergeVariableMap.get(variableKey));
                    }
                }
            } else {
                Map<String, Object> groupVariableMap = new HashMap<>();
                groupVariableMap.putAll(waitMergeVariableMap);
                this.VARIABLES.put(group, groupVariableMap);
            }
        }
    }


    /**
     * 检查是否存在
     *
     * @param key 指定Key
     * @return 存在时返回true，不存在时返回false
     */
    public boolean containsKey(String key) {
        if (VARIABLES.get(DEFAULT_GROUP) == null) {
            return false;
        }
        return VARIABLES.get(DEFAULT_GROUP).containsKey(key);
    }

    /**
     * 检查是否存在
     *
     * @param e 枚举类型key
     * @return 存在时返回true，不存在时返回false
     */
    public boolean containsKey(Enum<?> e) {
        return this.containsKey(e.toString());
    }
}
