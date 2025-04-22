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
 * 告警类型定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum AlarmType {
    /**
     * 湿度过低
     */
    LowHumidity("湿度过低"),
    /**
     * 湿度过高
     */
    HighHumidity("湿度过高"),
    /**
     * 温度过高
     */
    HighTemperature("温度过高"),
    /**
     * 温度过低
     */
    LowTemperature("温度过低");

    private final String description;

    AlarmType(String description) {
        this.description = description;
    }
}
