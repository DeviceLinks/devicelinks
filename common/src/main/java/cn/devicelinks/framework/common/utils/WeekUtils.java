package cn.devicelinks.framework.common.utils;

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
