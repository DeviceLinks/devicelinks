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

/**
 * 全局参数数据类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum GlobalSettingDataType {
    /**
     * 年月日时分秒
     */
    DateTime,
    /**
     * 年月日
     */
    Date,
    /**
     * 时分秒
     */
    Time,
    /**
     * true/false
     */
    Bool,
    /**
     * string
     */
    String,
    /**
     * 整数
     */
    Number,
    /**
     * 浮点类型
     */
    Decimal
}
