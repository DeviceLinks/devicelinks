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

package cn.devicelinks.framework.common.authorization;

import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.common.pojos.SysUserSession;

/**
 * 用户详情数据提供者
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SecurityUserDetailsProvider {
    /**
     * 获取会话ID
     * @return {@link SysUserSession#getId()}
     */
    String getSessionId();
    /**
     * 获取当前登录用户基本信息
     *
     * @return {@link SysUser}
     */
    SysUser getUser();

    /**
     * 获取用户部门
     *
     * @return {@link SysDepartment}
     */
    SysDepartment getUserDepartment();
}
