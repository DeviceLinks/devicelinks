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

package cn.devicelinks.console.service;

import cn.devicelinks.console.model.page.PageRequest;
import cn.devicelinks.framework.common.UserActivateMethod;
import cn.devicelinks.framework.jdbc.model.dto.UserDTO;
import cn.devicelinks.console.model.UsersQuery;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

import java.time.LocalDateTime;

/**
 * 用户业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysUserService extends BaseService<SysUser, String> {
    /**
     * 添加用户
     *
     * @param sysUser            {@link SysUser} 用户实例
     * @param userActivateMethod 用户激活方式
     * @return {@link SysUser}
     */
    SysUser addUser(SysUser sysUser, UserActivateMethod userActivateMethod);

    /**
     * 更新用户基本信息
     *
     * @param sysUser {@link SysUser}
     * @return 更新后的用户对象实例 {@link SysUser}
     */
    SysUser updateUser(SysUser sysUser);

    /**
     * 根据账号查询用户
     *
     * @param account {@link SysUser#getAccount()}
     * @return {@link SysUser}
     */
    SysUser selectByAccount(String account);

    /**
     * 更新账号最后登录时间
     *
     * @param userId        用户ID{@link SysUser#getId()}
     * @param lastLoginTime 最后登录时间 {@link SysUser#getLastLoginTime()}
     */
    void updateLastLoginTime(String userId, LocalDateTime lastLoginTime);

    /**
     * 分页获取用户列表
     *
     * @param query {@link UsersQuery}
     * @return {@link SysUser}
     */
    PageResult<UserDTO> getUsers(UsersQuery query, PageRequest pageRequest);

    /**
     * 删除用户
     *
     * @param userId 用户ID {@link SysUser#getId()}
     */
    void deleteUser(String userId);

    /**
     * 更新用户启用状态
     *
     * @param userId  用户ID {@link SysUser#getId()}
     * @param enabled 是否启用
     */
    void updateEnabled(String userId, boolean enabled);
}
