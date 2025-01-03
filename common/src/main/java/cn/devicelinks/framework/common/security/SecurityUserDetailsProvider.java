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

package cn.devicelinks.framework.common.security;

import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.common.pojos.SysPosition;
import cn.devicelinks.framework.common.pojos.SysUser;

import java.util.List;

/**
 * 用户详情数据提供者
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SecurityUserDetailsProvider {
    /**
     * 获取当前登录用户基本信息
     *
     * @return {@link SysUser}
     */
    SysUser getUser();

    /**
     * 获取用户岗位
     *
     * @return {@link SysPosition}
     */
    SysPosition getUserPosition();

    /**
     * 获取当前登录用户的租户ID
     *
     * @return {@link SysUser#getTenantId()}
     */
    String getTenantId();

    /**
     * 获取用户授权的用户组列表
     *
     * @return 用户组列表
     * @see cn.devicelinks.framework.common.pojos.SysGroupUser
     */
    List<SysGroup> getUserGroups();

}
