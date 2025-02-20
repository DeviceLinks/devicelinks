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
 * OTA升级批次状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum OtaUpgradeBatchState {
    /**
     * 待升级
     */
    Wait("待升级"),
    /**
     * 升级中
     */
    Upgrading("升级中"),
    /**
     * 已完成
     */
    Completed("已完成"),
    /**
     * 已取消
     */
    Cancelled("已取消"),
    /**
     * 未完成
     */
    Unfinished("未完成");

    private final String description;

    OtaUpgradeBatchState(String description) {
        this.description = description;
    }
}
