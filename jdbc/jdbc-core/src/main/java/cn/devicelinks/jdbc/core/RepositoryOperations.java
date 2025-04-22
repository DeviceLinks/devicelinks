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

import cn.devicelinks.jdbc.core.mapper.ResultRowMapper;
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
     * 查询单个数据
     *
     * @param sql        执行SQL
     * @param clazz      数据类型
     * @param parameters 参数值列表
     * @param <T>        数据类型
     * @return 数据值
     */
    <T> T queryForObject(String sql, Class<T> clazz, Object... parameters);

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
     * 更新数据执行方法
     *
     * @param sql        更新SQL
     * @param parameters 更新SQL所需要的参数列表，需要跟占位符一一对应
     * @return 数据更新影响的行数
     */
    int update(String sql, Object... parameters);

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
