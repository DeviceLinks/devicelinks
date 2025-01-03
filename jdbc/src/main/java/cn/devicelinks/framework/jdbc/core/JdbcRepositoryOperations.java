package cn.devicelinks.framework.jdbc.core;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.jdbc.core.mapper.ResultRowMapper;
import cn.devicelinks.framework.jdbc.core.printer.SqlPrinter;
import lombok.Getter;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * {@link RepositoryOperations}JDBC实现类
 * <p>
 * 封装{@link JdbcOperations}常用的方法，提供给拦截器进行方法拦截
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class JdbcRepositoryOperations implements RepositoryOperations {
    private final JdbcOperations jdbcOperations;
    private final SqlPrinter sqlPrinter;

    public JdbcRepositoryOperations(JdbcOperations jdbcOperations, SqlPrinter sqlPrinter) {
        this.jdbcOperations = jdbcOperations;
        this.sqlPrinter = sqlPrinter;
    }

    @Override
    public <T> List<T> query(String sql, ResultRowMapper<T> resultRowMapper, Object... parameters) {
        List<T> resultList = null;
        try {
            if (!ObjectUtils.isEmpty(parameters)) {
                resultList = this.jdbcOperations.query(sql, resultRowMapper, parameters);
            } else {
                resultList = this.jdbcOperations.query(sql, resultRowMapper);
            }
        } finally {
            // @formatter:off
            this.sqlPrinter.print(RepositoryMethod.Query,
                    sql,
                    parameters,
                    resultRowMapper.getAllRowValueMap(),
                    resultList != null ? resultList.size() : Constants.ZERO);
            // @formatter:on
        }
        return resultList;
    }

    @Override
    public <T> List<T> query(String sql, ResultRowMapper<T> resultRowMapper, List<SqlParameterValue> parameterValues) {
        List<T> resultList = null;
        try {
            if (!ObjectUtils.isEmpty(parameterValues)) {
                PreparedStatementSetter preparedStatementSetter = new ArgumentPreparedStatementSetter(parameterValues.toArray(SqlParameterValue[]::new));
                resultList = this.jdbcOperations.query(sql, preparedStatementSetter, resultRowMapper);
            } else {
                resultList = this.jdbcOperations.query(sql, resultRowMapper);
            }
        } finally {
            // @formatter:off
            this.sqlPrinter.print(RepositoryMethod.Query,
                    sql,
                    !ObjectUtils.isEmpty(parameterValues) ? parameterValues.stream().map(SqlParameterValue::getValue).toArray(Object[]::new) : null,
                    resultRowMapper.getAllRowValueMap(),
                    resultList != null ? resultList.size() : Constants.ZERO);
            // @formatter:on
        }
        return resultList;
    }

    @Override
    public int update(String sql, List<SqlParameterValue> sqlParameterValues) {
        return this.executeUpdate(sql, sqlParameterValues, RepositoryMethod.Update);
    }

    @Override
    public int insert(String sql, List<SqlParameterValue> sqlParameterValues) {
        return this.executeUpdate(sql, sqlParameterValues, RepositoryMethod.Insert);
    }

    @Override
    public int delete(String sql, List<SqlParameterValue> sqlParameterValues) {
        return this.executeUpdate(sql, sqlParameterValues, RepositoryMethod.Delete);
    }

    private int executeUpdate(String sql, List<SqlParameterValue> sqlParameterValues, RepositoryMethod method) {
        int affectedRows = Constants.ZERO;
        try {
            PreparedStatementSetter preparedStatementSetter = new ArgumentPreparedStatementSetter(sqlParameterValues.toArray(SqlParameterValue[]::new));
            affectedRows = this.jdbcOperations.update(sql, preparedStatementSetter);
        } finally {
            Object[] parameterValues = sqlParameterValues.stream().map(SqlParameterValue::getValue).toArray(Object[]::new);
            this.sqlPrinter.print(method, sql, parameterValues, null, affectedRows);
        }
        return affectedRows;
    }
}
