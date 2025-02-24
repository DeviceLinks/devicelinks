package cn.devicelinks.framework.jdbc.core.mapper.value.support;


import cn.devicelinks.framework.jdbc.core.mapper.value.ColumnValueMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 将{@link java.sql.Types#TIMESTAMP}类型列值转换为{@link LocalDateTime}的映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TimestampColumnValueMapper implements ColumnValueMapper<Long, Timestamp> {
    @Override
    public Timestamp toColumn(Long originalValue, String columnName) {
        return originalValue != null ? new Timestamp(originalValue) : null;
    }

    @Override
    public Long fromColumn(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp != null ? timestamp.getTime() : null;
    }
}
