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

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.SysUserSession;
import cn.devicelinks.jdbc.core.Repository;

import java.time.LocalDateTime;

/**
 * 用户会话数据接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysUserSessionRepository extends Repository<SysUserSession, String> {
    /**
     * 根据用户名查询最后的会话对象
     *
     * @param username {@link SysUserSession#getUsername()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectLastByUsername(String username);

    /**
     * 根据Token查询会话
     *
     * @param token {@link SysUserSession#getTokenValue()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectByToken(String token);

    /**
     * 根据用户名以及Token查询会话
     *
     * @param username {@link SysUserSession#getUsername()}
     * @param token    {@link SysUserSession#getTokenValue()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectByUsernameAndToken(String username, String token);

    /**
     * 更新最后活跃时间
     *
     * @param sessionId      会话ID {@link SysUserSession#getId()}
     * @param lastActiveTime 最后活跃时间 {@link SysUserSession#getLastActiveTime()}
     */
    void updateLastActiveTime(String sessionId, LocalDateTime lastActiveTime);
}
