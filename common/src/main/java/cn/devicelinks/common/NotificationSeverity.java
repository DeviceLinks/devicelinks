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
 * 通知严重性
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum NotificationSeverity {
    /**
     * 严重
     */
    Danger("严重"),
    /**
     * 重要
     */
    Important("重要"),
    /**
     * 次要
     */
    Secondary("次要"),
    /**
     * 警告
     */
    Warn("警告"),
    /**
     * 不确定
     */
    Uncertain("不确定");

    private final String description;

    NotificationSeverity(String description) {
        this.description = description;
    }
}
