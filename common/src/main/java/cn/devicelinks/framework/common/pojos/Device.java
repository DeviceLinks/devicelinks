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

package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.DeviceStatus;
import cn.devicelinks.framework.common.DeviceType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备基本信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Device implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String departmentId;
    private String productId;
    private String deviceCode;
    private String deviceSecret;
    private DeviceType deviceType;
    private String name;
    private DeviceStatus status;
    private List<String> tags;
    private LocalDateTime activationTime;
    private LocalDateTime lastOnlineTime;
    private LocalDateTime lastReportTime;
    private boolean enabled;
    private boolean deleted;
    private DeviceAddition addition;
    private String createBy;
    private LocalDateTime createTime;
    private String mark;
}
