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

package cn.devicelinks.api.support.authorization;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.SysDepartment;
import cn.devicelinks.entity.SysUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 认证用户附加信息
 * <p>
 * 给资源服务器提供获取用户相关附加信息，根据附加信息资源服务器可以做数据权限、操作日志、资源访问限制等
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class UserAuthorizedAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private SysUser user;
    private SysDepartment department;

    public UserAuthorizedAddition(SysUser user, SysDepartment department) {
        this.user = user;
        this.department = department;
    }

    public String getUserId() {
        return this.user.getId();
    }
}
