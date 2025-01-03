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
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysGroup.SYS_GROUP;

/**
 * 分组数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysGroupJdbcRepository extends JdbcRepository<SysGroup, String> implements SysGroupRepository {
    public SysGroupJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_GROUP, jdbcOperations);
    }
}
