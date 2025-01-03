/*
 *   Copyright (C) 2024  DeviceLinks
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

package cn.devicelinks.console.authorization;

import cn.devicelinks.console.service.SysGroupService;
import cn.devicelinks.console.service.SysPositionService;
import cn.devicelinks.console.service.SysUserService;
import cn.devicelinks.framework.common.GroupType;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.AuthorizedUserAddition;
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.common.pojos.SysPosition;
import cn.devicelinks.framework.common.pojos.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 用户附加信息JDBC数据加载实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class JdbcAuthorizedUserAdditionService implements AuthorizedUserAdditionService {
    private static final StatusCode ACCOUNT_NOT_FOUND = StatusCode.build("ACCOUNT_NOT_FOUND", "账号不存在, 无法进行认证.");
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPositionService positionService;
    @Autowired
    private SysGroupService groupService;

    @Override
    public AuthorizedUserAddition selectByUsername(String username) {
        SysUser user = userService.selectByAccount(username);
        if (user == null) {
            throw new DeviceLinksAuthorizationException(ACCOUNT_NOT_FOUND);
        }
        // @formatter:on
        SysPosition position = null;
        if (!ObjectUtils.isEmpty(user.getPositionId())) {
            position = positionService.selectById(user.getPositionId());
        }
        List<SysGroup> userGroups = groupService.selectByUserId(GroupType.user, user.getId());
        return new AuthorizedUserAddition(user, position, userGroups);
    }
}
