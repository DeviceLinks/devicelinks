package cn.devicelinks.framework.jdbc.core.mapper.value.support;

import cn.devicelinks.framework.jdbc.core.mapper.value.ColumnValueMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字节数组列值映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ByteArrayColumnValueMapper implements ColumnValueMapper<byte[], byte[]> {
    @Override
    public byte[] toColumn(byte[] originalValue, String columnName) {
        return originalValue;
    }

    @Override
    public byte[] fromColumn(ResultSet rs, String columnName) throws SQLException {
        return rs.getBytes(columnName);
    }
}
