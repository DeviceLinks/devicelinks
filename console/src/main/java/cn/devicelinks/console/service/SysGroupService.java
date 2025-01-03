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

package cn.devicelinks.console.service;

import cn.devicelinks.framework.common.GroupType;
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.common.pojos.SysGroupUser;
import cn.devicelinks.framework.jdbc.BaseService;

import java.util.List;

/**
 * 组业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysGroupService extends BaseService<SysGroup, String> {
    /**
     * 查询指定用户ID的
     *
     * @param userId 用户ID {@link SysGroupUser#getUserId()}
     * @return {@link SysGroup#getId()}
     */
    List<SysGroup> selectByUserId(GroupType type, String userId);
}
