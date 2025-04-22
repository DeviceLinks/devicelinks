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

package cn.devicelinks.console.startup.listener;

import cn.devicelinks.service.system.SysUserService;
import cn.devicelinks.common.UserIdentity;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.common.startup.AbstractStartupEventListener;
import cn.devicelinks.common.startup.ServerStartupEvent;
import cn.devicelinks.common.utils.ObjectIdUtils;
import cn.devicelinks.common.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 初始化"admin"账号密码监听器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
@Slf4j
public class InitializationAdminPasswordListener extends AbstractStartupEventListener {
    /**
     * The admin account
     */
    private static final String SYS_ADMIN_ACCOUNT = "admin";

    private final SysUserService userService;

    public InitializationAdminPasswordListener(SysUserService userService) {
        this.userService = userService;
    }

    @Override
    public void invokeListening(ServerStartupEvent event) {
        SysUser adminUser = this.userService.selectByAccount(SYS_ADMIN_ACCOUNT);
        if (adminUser == null) {
            // generator default random password
            String originalPassword = ObjectIdUtils.generateId();
            // @formatter:off
            adminUser = new SysUser()
                    .setId(UUIDUtils.generateNoDelimiter())
                    .setAccount(SYS_ADMIN_ACCOUNT)
                    .setName(SYS_ADMIN_ACCOUNT)
                    .setPwd(this.getDefaultPassword(originalPassword))
                    .setIdentity(UserIdentity.SystemAdmin)
                    .setEnabled(true)
                    .setDeleted(false)
                    .setCreateTime(LocalDateTime.now());
            // @formatter:on
            this.userService.insert(adminUser);
            log.info("The default password for the [{}] account has been generated, default plain text password is : {}", SYS_ADMIN_ACCOUNT, originalPassword);
        }
    }

    private String getDefaultPassword(String originalPassword) {
        // The password encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(originalPassword);
    }
}
