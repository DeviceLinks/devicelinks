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

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.SessionStatus;
import cn.devicelinks.framework.common.pojos.SysUserSession;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.sql.FusionCondition;
import cn.devicelinks.framework.jdbc.core.sql.LimitCondition;
import cn.devicelinks.framework.jdbc.repositorys.SysUserSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TSysUserSession.SYS_USER_SESSION;

/**
 * 用户会话业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysUserSessionServiceImpl extends BaseServiceImpl<SysUserSession, String, SysUserSessionRepository> implements SysUserSessionService {
    public SysUserSessionServiceImpl(SysUserSessionRepository repository) {
        super(repository);
    }

    @Override
    public SysUserSession selectLastByUserId(String userId) {
        // @formatter:off
        FusionCondition fusionCondition = FusionCondition.withCondition(SYS_USER_SESSION.USER_ID.eq(userId))
                .sort(SYS_USER_SESSION.EXPIRES_TIME.desc())
                .limit(LimitCondition.withLimit(Constants.ONE))
                .build();
        // @formatter:on
        List<SysUserSession> userSessionList = this.repository.select(fusionCondition);
        return ObjectUtils.isEmpty(userSessionList) ? null : userSessionList.getFirst();
    }

    @Override
    public SysUserSession selectByToken(String token) {
        return this.repository.selectByToken(token);
    }

    @Override
    public void updateLogoutTime(String token) {
        this.repository.update(List.of(
                SYS_USER_SESSION.STATUS.set(SessionStatus.LoggedOut),
                SYS_USER_SESSION.LOGOUT_TIME.set(LocalDateTime.now())
        ), SYS_USER_SESSION.TOKEN_VALUE.eq(token));
    }
}
