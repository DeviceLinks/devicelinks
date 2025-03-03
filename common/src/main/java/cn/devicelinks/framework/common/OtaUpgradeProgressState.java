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
 * OTA升级进度状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum OtaUpgradeProgressState {
    /**
     * 等待推送
     */
    WaitPush("等待推送"),
    /**
     * 等待升级
     */
    Wait("等待升级"),
    /**
     * 升级成功
     */
    Success("升级成功"),
    /**
     * 升级失败
     */
    Failure("升级失败"),
    /**
     * 取消升级
     */
    Cancel("取消升级"),
    ;
    private final String description;

    OtaUpgradeProgressState(String description) {
        this.description = description;
    }
}
