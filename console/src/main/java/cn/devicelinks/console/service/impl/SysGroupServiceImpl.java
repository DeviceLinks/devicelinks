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

package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.SysGroupService;
import cn.devicelinks.framework.common.GroupType;
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.sql.Dynamic;
import cn.devicelinks.framework.jdbc.repositorys.SysGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TSysGroup.SYS_GROUP;

/**
 * 组业务逻辑接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysGroupServiceImpl extends BaseServiceImpl<SysGroup, String, SysGroupRepository> implements SysGroupService {
    // @formatter:off
    private static final String SELECT_BY_USER_ID_DYNAMIC_SQL = "select sg.*\n" +
            "from sys_group sg\n" +
            "         left join sys_group_user sgu on sgu.group_id = sg.id\n" +
            "where sg.deleted is false and sgu.user_id = ? and sg.type = ?";
    // @formatter:on
    public SysGroupServiceImpl(SysGroupRepository repository) {
        super(repository);
    }

    @Override
    public List<SysGroup> selectByUserId(GroupType type, String userId) {
        Dynamic dynamic = Dynamic.buildSelect(SELECT_BY_USER_ID_DYNAMIC_SQL, SYS_GROUP.getColumns(), SysGroup.class);
        return repository.dynamicSelect(dynamic, userId, type.toString());
    }
}
