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

package cn.devicelinks.console;

import cn.devicelinks.framework.common.utils.RegexUtils;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 排序属性单元测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SortPropertiesTest {
    public static final Pattern PROPERTY_PATTERN = Pattern.compile("^[\\p{L}0-9_-]+$"); // Unicode letters, numbers, '_' and '-' allowed

    public static void main(String[] args) {
        System.out.println(isValidProperty("create_time"));
    }

    public static boolean isValidProperty(String key) {
        return StringUtils.isEmpty(key) || RegexUtils.matches(key, PROPERTY_PATTERN);
    }
}
