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

import cn.devicelinks.common.annotation.ApiEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@ApiEnum
public enum AttributeDataType {
    /**
     * 整型
     */
    INTEGER("整型"),
    /**
     * 浮点型
     */
    DOUBLE("浮点型"),
    /**
     * 枚举
     */
    ENUM("枚举"),
    /**
     * 布尔
     */
    BOOLEAN("布尔"),
    /**
     * 字符串
     */
    STRING("字符串"),
    /**
     * 日期
     */
    DATE("日期"),
    /**
     * 日期时间
     */
    DATETIME("日期时间"),
    /**
     * 时间
     */
    TIME("时间"),
    /**
     * 时间戳
     */
    TIMESTAMP("时间戳"),
    /**
     * JSON
     */
    JSON("JSON"),
    /**
     * 数组
     */
    ARRAY("数组");

    private final String description;

    AttributeDataType(String description) {
        this.description = description;
    }

    /**
     * 验证对象是否匹配指定类型
     *
     * @param value 要验证的对象
     * @throws IllegalArgumentException 当类型不匹配时抛出
     */
    public boolean validate(Object value) throws IllegalArgumentException {
        Assert.notNull(value, "value must not be null");
        boolean isValid = true;
        try {
            switch (this) {
                case STRING:
                    // ...
                    break;
                case INTEGER:
                    Integer.parseInt(value.toString());
                    break;
                case DOUBLE:
                    Double.parseDouble(value.toString());
                    break;
                case ENUM:
                    isValid = value instanceof Enum;
                    break;
                case BOOLEAN:
                    // Validate the value as a boolean (true/false)
                    if (!Boolean.TRUE.toString().equalsIgnoreCase(value.toString()) && !Boolean.FALSE.toString().equalsIgnoreCase(value.toString())) {
                        isValid = false;
                    }
                    break;
                case DATE:
                    LocalDate.parse(value.toString());
                    break;
                case DATETIME:
                    LocalDateTime.parse(value.toString());
                    break;
                case TIME:
                    LocalTime.parse(value.toString());
                    break;
                case TIMESTAMP:
                    long ts = Long.parseLong(value.toString());
                    if (ts < 0 || ts > 4102444800000L) {
                        isValid = false;
                    }
                    break;
                case ARRAY:
                    isValid = value.getClass().isArray() || value instanceof List;
                    break;
                case JSON:
                    new ObjectMapper().readTree(value.toString());
                    break;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
}
