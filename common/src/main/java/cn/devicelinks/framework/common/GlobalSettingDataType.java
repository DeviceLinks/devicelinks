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

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 全局参数数据类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum GlobalSettingDataType {
    /**
     * 年月日时分秒
     */
    DateTime("年月日时分秒"),
    /**
     * 年月日
     */
    Date("年月日"),
    /**
     * 时分秒
     */
    Time("时分秒"),
    /**
     * true/false
     */
    Bool("布尔"),
    /**
     * string
     */
    String("字符串"),
    /**
     * 整数
     */
    Number("整型"),
    /**
     * 浮点类型
     */
    Decimal("浮点类型");

    private final String description;

    GlobalSettingDataType(java.lang.String description) {
        this.description = description;
    }
}
