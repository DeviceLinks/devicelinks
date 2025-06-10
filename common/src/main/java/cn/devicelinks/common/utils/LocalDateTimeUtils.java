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

import cn.devicelinks.common.Constants;
import org.springframework.util.ObjectUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * {@link LocalDateTime}时间对象工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LocalDateTimeUtils {
    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT_YYYY_MM_DD);
    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
    public static final DateTimeFormatter FORMATTER_HH_MM_SS = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT_HH_MM_SS);

    /**
     * 将时间戳转换为指定格式的时间字符串
     *
     * @param time 时间戳
     * @return 时间字符串
     */
    public static String convertTimeToString(Long time) {
        if (ObjectUtils.isEmpty(time)) {
            return null;
        }
        return FORMATTER_YYYY_MM_DD_HH_MM_SS.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * 将时间字符串转换为指定格式的时间戳
     *
     * @param time 时间字符串
     * @return 时间戳
     */
    public static Long convertTimeToLong(String time) {
        if (ObjectUtils.isEmpty(time)) {
            throw new IllegalArgumentException("时间参数异常！");
        }
        LocalDateTime parse = LocalDateTime.parse(time, FORMATTER_YYYY_MM_DD_HH_MM_SS);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将{@link LocalDateTime}转换为指定格式的字符串
     *
     * @param localDateTime {@link LocalDateTime}
     * @return 时间字符串
     */
    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        return FORMATTER_YYYY_MM_DD_HH_MM_SS.format(localDateTime);
    }

    /**
     * 将时间字符串转换为{@link LocalDate}
     *
     * @param localDate 日期字符串
     * @return {@link LocalDateTime}
     */
    public static LocalDate convertStringToLocalDate(String localDate) {
        return LocalDate.parse(localDate, FORMATTER_YYYY_MM_DD);
    }

    /**
     * 将{@link LocalDate}转换为指定格式的字符串
     *
     * @param localDate {@link LocalDate}
     * @return 时间字符串
     */
    public static String convertLocalDateToString(LocalDate localDate) {
        return FORMATTER_YYYY_MM_DD.format(localDate);
    }

    /**
     * 将时间字符串转换为{@link LocalDateTime}
     *
     * @param time 时间字符串
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime convertStringToLocalDateTime(String time) {
        return LocalDateTime.parse(time, FORMATTER_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将时间字符串转换为{@link LocalTime}
     *
     * @param time 时间字符串
     * @return {@link LocalTime}
     */
    public static LocalTime convertStringToLocalTime(String time) {
        return LocalTime.parse(time, FORMATTER_HH_MM_SS);
    }

    /**
     * 取本月第一天
     */
    public static LocalDate firstDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 取本月第N天
     */
    public static LocalDate dayOfThisMonth(int n) {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(n);
    }

    /**
     * 取本月最后一天
     */
    public static LocalDate lastDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 取本月第一天的开始时间
     */
    public static LocalDateTime startOfThisMonth() {
        return LocalDateTime.of(firstDayOfThisMonth(), LocalTime.MIN);
    }


    /**
     * 取本月最后一天的结束时间
     */
    public static LocalDateTime endOfThisMonth() {
        return LocalDateTime.of(lastDayOfThisMonth(), LocalTime.MAX);
    }
}
