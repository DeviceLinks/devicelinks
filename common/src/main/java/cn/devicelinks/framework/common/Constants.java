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
 * 常量定义
 *
 * @author 恒宇少年
 */
public interface Constants {
    /**
     * 整型0
     */
    int ZERO = 0;
    /**
     * 整型1
     */
    int ONE = 1;
    /**
     * 空字符串
     */
    String EMPTY_STRING = "";
    /**
     * 空格
     */
    String SPACE = " ";
    /**
     * 多个值之间的分隔符
     */
    String SEPARATOR = ",";
    /**
     * 空字节
     */
    byte EMPTY_BYTE = 0b00000000;
    /**
     * 值类型为枚举时用于获取枚举中定义的对应"label"的字段
     */
    String ENUM_OBJECT_LABEL_FIELD = "description";
    /**
     * 枚举值在前端的显示样式
     */
    String ENUM_SHOW_STYLE_FIELD = "showStyle";
    /**
     * 日期/时间格式：yyyy-MM-dd
     */
    String DATE_TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期/时间格式：yyyy-MM-dd HH:mm:ss
     */
    String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式：HH:mm:ss
     */
    String DATE_TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
}
