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

package cn.devicelinks.framework.common.jackson2.security;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.common.pojos.SysPosition;
import cn.devicelinks.framework.common.pojos.SysUser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.Serial;

/**
 * Jackson2 Security {@link Module}
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceLinksSecurityJackson2Module extends SimpleModule {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    public DeviceLinksSecurityJackson2Module() {
        super(DeviceLinksSecurityJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(DeviceLinksUserDetails.class, SecurityUserDetailsMixin.class);
        context.setMixInAnnotations(SysUser.class, SysUserMixin.class);
        context.setMixInAnnotations(SysPosition.class, SysPositionMixin.class);
        context.setMixInAnnotations(SysGroup.class, SysGroupMixin.class);
    }
}
