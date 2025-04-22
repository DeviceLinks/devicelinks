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

package cn.devicelinks.common.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 星期工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class WeekUtils {
    private static final Map<Integer, String> WEEK_MAP = new HashMap<>() {
        {
            put(Calendar.SUNDAY, "星期日");
            put(Calendar.MONDAY, "星期一");
            put(Calendar.TUESDAY, "星期二");
            put(Calendar.WEDNESDAY, "星期三");
            put(Calendar.THURSDAY, "星期四");
            put(Calendar.FRIDAY, "星期五");
            put(Calendar.SATURDAY, "星期六");
        }
    };

    public static String getTodayWeek() {
        Calendar calendar = Calendar.getInstance();
        return WEEK_MAP.get(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static String getWeek(int week) {
        return WEEK_MAP.get(week);
    }

    public static List<String> getWeeks(int[] weeks) {
        return Arrays.stream(weeks).mapToObj(WEEK_MAP::get).collect(Collectors.toList());
    }
}
