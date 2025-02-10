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

package cn.devicelinks.framework.common;

import lombok.Getter;

/**
 * 功能模块数据类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum FunctionModuleDataType {
    INTEGER("整数型", "int32"),
    LONG("长整数型", "long64"),
    FLOAT("单精度浮点型", "float"),
    DOUBLE("双精度浮点型", "double"),
    ENUM("枚举型", "enum"),
    BOOLEAN("布尔型", "boolean"),
    STRING("字符串", "string"),
    DATE("日期", "date"),
    DATE_TIME("日期时间", "datetime"),
    TIME("时间", "time"),
    TIMESTAMP("时间戳", "timestamp"),
    OBJECT("对象", "object"),
    ARRAY("数组", "array"),
    ;
    private final String name;
    private final String value;

    FunctionModuleDataType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 根据值获取数据类型
     *
     * @param value 值
     * @return 数据类型
     */
    public static FunctionModuleDataType fromValue(String value) {
        for (FunctionModuleDataType type : FunctionModuleDataType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
