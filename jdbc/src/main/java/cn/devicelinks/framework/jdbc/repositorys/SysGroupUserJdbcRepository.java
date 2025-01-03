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
import cn.devicelinks.framework.common.pojos.SysGroupUser;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysGroupUser.SYS_GROUP_USER;

/**
 * 分组与用户数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysGroupUserJdbcRepository extends JdbcRepository<SysGroupUser, String> implements SysGroupUserRepository {
    public SysGroupUserJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_GROUP_USER, jdbcOperations);
    }
}
