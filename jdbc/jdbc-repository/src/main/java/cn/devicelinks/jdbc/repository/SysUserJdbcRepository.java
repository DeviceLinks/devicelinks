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

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.api.model.dto.UserDTO;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.definition.DynamicColumn;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.Dynamic;
import cn.devicelinks.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;
import cn.devicelinks.jdbc.tables.TSysDepartment;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TSysUser.SYS_USER;

/**
 * 管理用户JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class SysUserJdbcRepository extends JdbcRepository<SysUser, String> implements SysUserRepository {
    // @formatter:off
    private static final String SELECT_USER_DTO_SQL = "select su.*, sd.name department_name" +
            " from sys_user su" +
            " left join sys_department sd on sd.id = su.department_id where su.deleted is false";
    // @formatter:on

    public SysUserJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_USER, jdbcOperations);
    }

    @Override
    public SysUser selectByAccount(String account) {
        return selectOne(SYS_USER.ACCOUNT.eq(account));
    }

    @Override
    public PageResult<UserDTO> selectByPage(List<SearchFieldCondition> searchFieldConditions, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_USER_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(SYS_USER.getColumns());
                    resultColumns.add(DynamicColumn.withColumn(TSysDepartment.SYS_DEPARTMENT.NAME).alias("department_name").build());
                })
                .appendSearchFieldCondition(SYS_USER, searchFieldConditions, consumer -> consumer.tableAlias("su"))
                .sort(sortCondition);
        // @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(UserDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.page(dynamic, pageQuery, wrapper.parameters());
    }

    @Override
    public List<UserDTO> selectUsers(List<SearchFieldCondition> searchFieldConditions) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_USER_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(SYS_USER.getColumns());
                    resultColumns.add(DynamicColumn.withColumn(TSysDepartment.SYS_DEPARTMENT.NAME).alias("department_name").build());
                })
                .appendSearchFieldCondition(SYS_USER, searchFieldConditions, consumer -> consumer.tableAlias("su"));
        // @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(UserDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.dynamicSelect(dynamic, wrapper.parameters());
    }
}
