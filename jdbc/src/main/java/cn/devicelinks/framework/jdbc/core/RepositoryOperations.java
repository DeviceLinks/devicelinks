package cn.devicelinks.framework.jdbc.core;

import cn.devicelinks.framework.jdbc.core.mapper.ResultRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;

import java.util.List;

/**
 * {@link Repository}数据操作封装类接口方法定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface RepositoryOperations {
    /**
     * 查询数据列表
     *
     * @param sql             执行SQL
     * @param parameters      执行SQL所需要的参数列表
     * @param resultRowMapper 每一行结果映射{@link RowMapper}实例
     * @param <T>             数据类型
     * @return 数据列表
     */
    <T> List<T> query(String sql, ResultRowMapper<T> resultRowMapper, Object... parameters);

    /**
     * 查询数据列表
     *
     * @param sql             执行SQL
     * @param parameterValues 执行SQL所需要的{@link SqlParameterValue}列表
     * @param resultRowMapper 每一行结果映射{@link RowMapper}实例
     * @param <T>             数据类型
     * @return 数据列表
     */
    <T> List<T> query(String sql, ResultRowMapper<T> resultRowMapper, List<SqlParameterValue> parameterValues);

    /**
     * 更新数据执行方法
     *
     * @param sql                更新SQL
     * @param sqlParameterValues 更新SQL所需要的{@link SqlParameterValue}列表
     * @return 数据更新影响的行数
     */
    int update(String sql, List<SqlParameterValue> sqlParameterValues);

    /**
     * 新增数据执行方法
     *
     * @param sql                新增SQL
     * @param sqlParameterValues 新增SQL所需要的{@link SqlParameterValue}列表
     * @return 新增数据影响的行数
     */
    int insert(String sql, List<SqlParameterValue> sqlParameterValues);

    /**
     * 删除数据执行方法
     *
     * @param sql                删除SQL
     * @param sqlParameterValues 删除SQL所需要的{@link SqlParameterValue}列表
     * @return 删除数据影响的行数
     */
    int delete(String sql, List<SqlParameterValue> sqlParameterValues);
}
