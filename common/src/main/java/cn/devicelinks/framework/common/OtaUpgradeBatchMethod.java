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
 * OTA升级批次升级方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum OtaUpgradeBatchMethod {
    /**
     * 静态升级
     */
    Static("静态升级"),
    /**
     * 动态升级
     */
    Dynamic("动态升级");

    private final String description;

    OtaUpgradeBatchMethod(String description) {
        this.description = description;
    }
}
