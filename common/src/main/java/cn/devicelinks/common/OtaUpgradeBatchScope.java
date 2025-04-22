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
 * OTA升级批次升级范围
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum OtaUpgradeBatchScope {
    /**
     * 全部设备
     */
    All("全部设备"),
    /**
     * 定向升级
     */
    Direction("定向升级"),
    /**
     * 区域升级
     */
    Area("区域升级"),
    /**
     * 灰度升级
     */
    Grayscale("灰度升级"),
    /**
     * 分组升级
     */
    Group("分组升级");

    private final String description;

    OtaUpgradeBatchScope(String description) {
        this.description = description;
    }
}
