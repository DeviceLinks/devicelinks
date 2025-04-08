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

package cn.devicelinks.console.authorization;

import cn.devicelinks.service.system.SysDepartmentService;
import cn.devicelinks.service.system.SysUserService;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.common.pojos.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

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
    private SysDepartmentService departmentService;

    @Override
    public UserAuthorizedAddition selectByUsername(String username) {
        SysUser user = userService.selectByAccount(username);
        if (user == null) {
            throw new DeviceLinksAuthorizationException(ACCOUNT_NOT_FOUND);
        }
        // @formatter:on
        SysDepartment department = null;
        if (!ObjectUtils.isEmpty(user.getDepartmentId())) {
            department = departmentService.selectById(user.getDepartmentId());
        }
        return new UserAuthorizedAddition(user, department);
    }
}
