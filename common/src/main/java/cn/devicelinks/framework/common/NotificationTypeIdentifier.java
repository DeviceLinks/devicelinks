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

package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 通知类型标识符
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum NotificationTypeIdentifier {
    /**
     * 告警
     */
    Alarm("告警"),
    /**
     * 设备生命周期
     */
    DeviceLifecycle("设备生命周期"),
    /**
     * 实体动作
     */
    DataEntityAction("实体动作"),
    /**
     * 规则引擎故障
     */
    RuleEngineFailure("规则引擎故障");
    
    private final String description;

    NotificationTypeIdentifier(String description) {
        this.description = description;
    }
}
