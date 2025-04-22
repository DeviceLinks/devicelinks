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

import cn.devicelinks.common.Constants;
import cn.devicelinks.entity.SysUserSession;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.sql.FusionCondition;
import cn.devicelinks.jdbc.core.sql.LimitCondition;
import cn.devicelinks.jdbc.tables.TSysUserSession;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TSysUserSession.SYS_USER_SESSION;

/**
 * 用户会话数据JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class SysUserSessionJdbcRepository extends JdbcRepository<SysUserSession, String> implements SysUserSessionRepository {
    public SysUserSessionJdbcRepository(JdbcOperations jdbcOperations) {
        super(TSysUserSession.SYS_USER_SESSION, jdbcOperations);
    }

    @Override
    public SysUserSession selectLastByUsername(String username) {
        // @formatter:off
        List<SysUserSession> sessionList = this.select(FusionCondition
                .withCondition(TSysUserSession.SYS_USER_SESSION.USERNAME.eq(username))
                .sort(TSysUserSession.SYS_USER_SESSION.EXPIRES_TIME.desc())
                .limit(LimitCondition.withLimit(Constants.ONE))
                .build());
        // @formatter:on
        return ObjectUtils.isEmpty(sessionList) ? null : sessionList.getFirst();
    }

    @Override
    public SysUserSession selectByToken(String token) {
        return this.selectOne(TSysUserSession.SYS_USER_SESSION.TOKEN_VALUE.eq(token));
    }

    @Override
    public SysUserSession selectByUsernameAndToken(String username, String token) {
        return this.selectOne(TSysUserSession.SYS_USER_SESSION.USERNAME.eq(username),
                TSysUserSession.SYS_USER_SESSION.TOKEN_VALUE.eq(token));
    }

    @Override
    public void updateLastActiveTime(String sessionId, LocalDateTime lastActiveTime) {
        this.update(List.of(TSysUserSession.SYS_USER_SESSION.LAST_ACTIVE_TIME.set(lastActiveTime)), TSysUserSession.SYS_USER_SESSION.ID.eq(sessionId));
    }
}
