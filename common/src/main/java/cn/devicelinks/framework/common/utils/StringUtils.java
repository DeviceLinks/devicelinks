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

package cn.devicelinks.framework.common.utils;

import com.google.common.base.CaseFormat;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author 恒宇少年
 * @since 1.0
 */
public class StringUtils {
    /**
     * 将字符串转换为驼峰格式首字母大写
     *
     * @param value 原始字符串
     * @return 转换后的字符串
     */
    public static String lowerUnderToUpperCamel(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, value);
    }

    /**
     * 将字符串转换为驼峰格式首字母小写
     *
     * @param value 原始字符串
     * @return 转换后的字符串
     */
    public static String lowerUnderToLowerCamel(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
    }

    /**
     * 首字母小写驼峰字符串转换为小写带下划线的字符串
     *
     * @param value 原始字符串
     * @return 转换后的字符串
     */
    public static String lowerCamelToLowerUnder(String value) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);
    }

    /**
     * 首字母小写驼峰字符串转换为首字母大写驼峰字符串
     *
     * @param value 原始字符串
     * @return 转换后的字符串
     */
    public static String lowerCamelToUpperCamel(String value) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, value);
    }

    /**
     * List转字符串，根据指定分隔符连接
     *
     * @param separator 分隔符
     * @param values    List列表
     * @return 拼接字符串
     */
    public static String joiner(String separator, List<String> values) {
        StringJoiner joiner = new StringJoiner(separator);
        values.forEach(joiner::add);
        return joiner.toString();
    }

    /**
     * 生成指定长度固定占位符根据指定分隔符的字符串
     *
     * @param separator   分隔符
     * @param placeholder 占位符
     * @param length      长度
     * @return 拼接字符串
     */
    public static String joiner(String separator, String placeholder, int length) {
        StringJoiner joiner = new StringJoiner(separator);
        for (int i = 0; i < length; i++) {
            joiner.add(placeholder);
        }
        return joiner.toString();
    }

    /**
     * 首字母大写
     *
     * @param inputString 待转换字符串
     * @return 转换后的字符串
     */
    public static String firstCharToUpperCase(String inputString) {
        char firstLetter = inputString.charAt(0);
        char capitalFirstLetter = Character.toUpperCase(firstLetter);
        return inputString.replace(inputString.charAt(0), capitalFirstLetter);
    }

    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }

}
