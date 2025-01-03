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

import cn.devicelinks.framework.common.pojos.SysUserSession;
import cn.devicelinks.framework.jdbc.BaseService;

/**
 * 用户会话业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysUserSessionService extends BaseService<SysUserSession, String> {
    /**
     * 查询用户最新的会话
     *
     * @param userId 用户ID {@link SysUserSession#getUserId()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectLastByUserId(String userId);

    /**
     * 查询指定令牌的会话
     *
     * @param token 会话令牌
     * @return {@link SysUserSession}
     */
    SysUserSession selectByToken(String token);

    /**
     * 更新指定令牌会话的登出时间
     *
     * @param token 会话令牌
     */
    void updateLogoutTime(String token);
}
