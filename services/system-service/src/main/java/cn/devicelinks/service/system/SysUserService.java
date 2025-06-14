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

package cn.devicelinks.service.system;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.common.UserActivateMethod;
import cn.devicelinks.entity.SysDepartment;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.jdbc.BaseService;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.api.model.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

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
     * @param query {@link SearchFieldQuery}
     * @return {@link UserDTO}
     */
    PageResult<UserDTO> getUsersWithPage(SearchFieldQuery query, PaginationQuery pageRequest);

    /**
     * 获取用户列表
     *
     * @param query 检索字段查询对象 {@link SearchFieldQuery}
     * @return {@link UserDTO}
     */
    List<UserDTO> getUsers(SearchFieldQuery query);

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

    /**
     * 批量更新用户列表的部门ID
     *
     * @param userIds      用户ID列表 {@link SysUser#getId()}
     * @param departmentId 部门ID {@link SysDepartment#getId()}
     */
    void batchUpdateDepartmentId(List<String> userIds, String departmentId);
}
