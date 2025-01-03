package cn.devicelinks.framework.jdbc.core.sql;

import lombok.Getter;

/**
 * 限制查询数据条数的条件
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class LimitCondition {
    private static final long DEFAULT_LIMIT = 20L;
    private static final long DEFAULT_OFFSET = 0L;
    private static final String LIMIT_SQL_PREFIX = " limit %d offset %d";
    private final long limit;
    private final long offset;

    private LimitCondition(long limit, long offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public static LimitCondition withLimit(long limit) {
        return new LimitCondition(limit, DEFAULT_OFFSET);
    }

    public static LimitCondition with(long limit, long offset) {
        return new LimitCondition(limit, offset);
    }

    public String getSql() {
        return String.format(LIMIT_SQL_PREFIX, this.limit, this.offset);
    }
}
