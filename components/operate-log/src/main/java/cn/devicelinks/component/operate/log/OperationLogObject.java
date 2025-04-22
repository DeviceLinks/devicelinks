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

package cn.devicelinks.component.operate.log;

import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 操作日志对象类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class OperationLogObject {

    private String sessionId;

    private LogAction action;

    private LogObjectType objectType;

    private String objectId;

    private Object beforeObject;

    private Object afterObject;

    private String activateData;

    private String msg;

    private boolean executionSucceed;

    private String operatorId;

    private String ipAddress;

    private String browser;

    private String os;

    private String failureReason;

    private Throwable failureCause;

    private LocalDateTime time = LocalDateTime.now();
}
