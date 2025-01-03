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

package cn.devicelinks.framework.common;

/**
 * 数据格式
 * <p>
 * 定义了设备原始上报数据的格式，不同的数据格式解析数据的方式不同
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum DataFormat {
    /**
     * JSON数据格式
     * <p>
     * 如：{"deviceCode":"xxxx","reportTime":"xxxx"}
     */
    JSON,
    /**
     * 二进制数据格式
     * <p>
     * 如：0100001010
     */
    BINARY,
    /**
     * 十进制数据格式
     * <p>
     * 如：deviceCode=xxxx,reportTime=xxxx
     */
    DECIMAL,
    /**
     * 十六进制数据格式
     * <p>
     * 如：685a68b337010200100213812
     */
    HEX,
    /**
     * 字符串
     */
    STRING
}
