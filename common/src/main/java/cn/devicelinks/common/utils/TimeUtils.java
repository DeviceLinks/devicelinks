package cn.devicelinks.common.utils;

import cn.devicelinks.common.Constants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TimeUtils {
    /**
     * 验证时间戳是否有效
     *
     * @param timestamp          请求时携带的时间戳
     * @param effectiveTimestamp 有效范围的时间戳
     * @return 返回true时表示验证通过
     */
    public static boolean validateTimestamp(long timestamp, long effectiveTimestamp) {
        long currentTimestamp = System.currentTimeMillis();
        return timestamp > Constants.ZERO && currentTimestamp - timestamp <= effectiveTimestamp;
    }

    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
