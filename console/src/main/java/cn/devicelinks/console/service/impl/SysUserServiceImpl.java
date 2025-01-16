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

package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.SysUserService;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String, SysUserRepository> implements SysUserService {
    public SysUserServiceImpl(SysUserRepository repository) {
        super(repository);
    }

    @Override
    public SysUser selectByAccount(String account) {
        return this.repository.selectByAccount(account);
    }

    @Override
    public void updateLastLoginTime(String userId, LocalDateTime lastLoginTime) {
        // @formatter:off
        /*this.repository.update(
                List.of(
                        SYS_USER.LAST_LOGIN_TIME.set(lastLoginTime)
                ),
                SYS_USER.ID.eq(userId)
        );*/
        // @formatter:on
    }
}
