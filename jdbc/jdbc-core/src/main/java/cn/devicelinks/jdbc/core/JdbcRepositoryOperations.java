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

package cn.devicelinks.jdbc.core;

import cn.devicelinks.common.Constants;
import cn.devicelinks.common.exception.DeviceLinksException;
import cn.devicelinks.jdbc.core.mapper.ResultRowMapper;
import cn.devicelinks.jdbc.core.printer.SqlPrinter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * {@link RepositoryOperations}JDBC实现类
 * <p>
 * 封装{@link JdbcOperations}常用的方法，提供给拦截器进行方法拦截
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@Slf4j
public class JdbcRepositoryOperations implements RepositoryOperations {

    public static final String RESULT_OBJECT_KEY = "ResultObject";
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DeviceLinksException("执行数据查询时遇到异常", e);
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DeviceLinksException("执行数据查询时遇到异常", e);
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
    public <T> T queryForObject(String sql, Class<T> clazz, Object... parameters) {
        T resultObject = null;
        try {
            resultObject = this.jdbcOperations.queryForObject(sql, clazz, parameters);
            return resultObject;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DeviceLinksException("执行数据查询时遇到异常", e);
        } finally {
            boolean printRow = resultObject != null;
            if (printRow && resultObject instanceof Number) {
                printRow = ((Integer) resultObject) > Constants.ZERO;
            }
            this.sqlPrinter.print(RepositoryMethod.Query, sql, parameters,
                    printRow ? List.of(Map.of(RESULT_OBJECT_KEY, resultObject)) : null,
                    printRow ? Constants.ONE : Constants.ZERO);
        }
    }

    @Override
    public int update(String sql, List<SqlParameterValue> sqlParameterValues) {
        return this.executeUpdate(sql, sqlParameterValues, RepositoryMethod.Update);
    }

    @Override
    public int update(String sql, Object... parameters) {
        int affectedRows = Constants.ZERO;
        try {
            affectedRows = this.jdbcOperations.update(sql, parameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DeviceLinksException("执行数据更新或删除时遇到异常", e);
        } finally {
            this.sqlPrinter.print(RepositoryMethod.Update, sql, parameters, null, affectedRows);
        }
        return affectedRows;
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DeviceLinksException("执行数据更新或删除时遇到异常", e);
        } finally {
            Object[] parameterValues = sqlParameterValues.stream().map(SqlParameterValue::getValue).toArray(Object[]::new);
            this.sqlPrinter.print(method, sql, parameterValues, null, affectedRows);
        }
        return affectedRows;
    }
}
