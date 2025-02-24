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

import cn.devicelinks.framework.common.DeviceDesiredOperationSource;
import cn.devicelinks.framework.common.DeviceDesiredOperationType;
import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 属性期望值
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceDesired implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String deviceId;
    private Map<String, Object> desiredDocument;
    private long version;
    private DeviceDesiredOperationType operationType;
    private DeviceDesiredOperationSource operationSource;
    private LocalDateTime effectiveTime;
    private LocalDateTime expireTime;
    private String createBy;
    private LocalDateTime createTime;
}
