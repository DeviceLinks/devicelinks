package cn.devicelinks.framework.common.utils;

/**
 * 单机版雪花算法ID生成器（不支持分布式）
 * <p>
 * 结构说明：
 * | 1位符号位 | 41位时间戳 | 10位固定标识 | 12位序列号 |
 * 特点：
 * - 单机部署时固定workerId和datacenterId为0
 * - 支持最大并发4096/ms
 * - 时间起始点：2020-08-08 08:08:08
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SnowflakeIdUtils {
    // 起始时间戳（2025-01-01 00:00:00）
    private static final long START_STAMP = 1735660800000L;

    // 序列号位数
    private static final long SEQUENCE_BITS = 12L;

    // 固定标识位数（5位workerId + 5位datacenterId）
    private static final long FIXED_BITS = 10L;

    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    // 固定标识（单机版固定为0）
    private static final long FIXED_ID = 0L;

    private static long lastStamp = -1L;

    private static long sequence = 0L;

    public static synchronized String generateId() {
        return String.valueOf(nextId());
    }
    
    public static synchronized long nextId() {
        long currStamp = getCurrentStamp();

        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards");
        }

        if (currStamp == lastStamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) { // 当前毫秒序列号用完
                currStamp = waitNextMillis(currStamp);
            }
        } else {
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << (FIXED_BITS + SEQUENCE_BITS)
                | FIXED_ID << SEQUENCE_BITS
                | sequence;
    }

    private static long waitNextMillis(long currStamp) {
        while (currStamp <= lastStamp) {
            currStamp = getCurrentStamp();
        }
        return currStamp;
    }

    private static long getCurrentStamp() {
        return System.currentTimeMillis();
    }
}
