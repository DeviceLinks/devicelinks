/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.Dynamic;
import cn.devicelinks.framework.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.model.dto.UserDTO;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ObjectUtils;

import static cn.devicelinks.framework.jdbc.tables.TSysUser.SYS_USER;

/**
 * 管理用户JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysUserJdbcRepository extends JdbcRepository<SysUser, String> implements SysUserRepository {
    // @formatter:off
    private static final String SELECT_USER_DTO_SQL = "select su.*, sd.name department_name" +
            " from sys_user su" +
            " left join sys_department sd on sd.id = su.department_id" +
            " where su.deleted is false";
    // @formatter:on
    private static final Column COLUMN_DEPARTMENT_NAME = Column.withName("department_name").build();

    public SysUserJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_USER, jdbcOperations);
    }

    @Override
    public SysUser selectByAccount(String account) {
        return selectOne(SYS_USER.ACCOUNT.eq(account));
    }

    @Override
    public PageResult<UserDTO> selectByPage(SysUser queryUser, int pageIndex, int pageSize) {
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(SELECT_USER_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(SYS_USER.getColumns());
                    resultColumns.add(COLUMN_DEPARTMENT_NAME);
                })
                .appendCondition(!ObjectUtils.isEmpty(queryUser.getDepartmentId()), SqlFederationAway.AND, SYS_USER.DEPARTMENT_ID.eq(queryUser.getDepartmentId()).tableAlias("su"))
                .appendCondition(!ObjectUtils.isEmpty(queryUser.getIdentity()), SqlFederationAway.AND, SYS_USER.IDENTITY.eq(queryUser.getIdentity()).tableAlias("su"))
                .appendCondition(!ObjectUtils.isEmpty(queryUser.getName()), SqlFederationAway.AND, SYS_USER.NAME.like(queryUser.getName()).tableAlias("su"))
                .sort(SortCondition.withColumn(SYS_USER.CREATE_TIME).desc())
                .resultType(UserDTO.class)
                .build();
        // @formatter:on

        Dynamic dynamic = wrapper.dynamic();
        return this.page(dynamic, PageQuery.of(pageIndex, pageSize), wrapper.parameters());
    }
}
