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

package cn.devicelinks.component.operate.log.expression;

import cn.devicelinks.common.Variables;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * 变量封装类
 * <p>
 * 用于格式化表达式
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ExpressionVariables extends Variables {
    /**
     * 添加Map参数集合到变量集合
     *
     * @param parameterValues Map参数集合
     */
    public void addVariables(Map<String, Object> parameterValues) {
        if (!ObjectUtils.isEmpty(parameterValues)) {
            parameterValues.keySet().forEach(parameter -> setVariable(parameter, parameterValues.get(parameter)));
        }
    }

    /**
     * 获取全部的变量
     *
     * @return 全部变量
     */
    public Map<String, Object> getAllVariables() {
        Map<String, Object> tempVariables = CollectionUtils.newHashMap(VARIABLES.get(DEFAULT_GROUP).size());
        tempVariables.putAll(VARIABLES.get(DEFAULT_GROUP));
        return tempVariables;
    }
}
