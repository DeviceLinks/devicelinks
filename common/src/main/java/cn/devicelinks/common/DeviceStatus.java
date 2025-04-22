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

package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum DeviceStatus {
    /**
     * 未激活，设备从未连接并上报数据
     */
    NotActivate("未激活", EnumShowStyle.Warning),
    /**
     * 已激活
     */
    Activated("已激活", EnumShowStyle.Processing),
    /**
     * 已激活，在线
     */
    Online("在线", EnumShowStyle.Success),
    /**
     * 已激活，离线
     */
    Offline("离线", EnumShowStyle.Default);

    private final String description;
    private final EnumShowStyle showStyle;

    DeviceStatus(String description, EnumShowStyle showStyle) {
        this.description = description;
        this.showStyle = showStyle;
    }
}
