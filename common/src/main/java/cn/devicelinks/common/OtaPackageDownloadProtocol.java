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
 * OTA升级包下载协议
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum OtaPackageDownloadProtocol {
    /**
     * HTTP协议
     */
    Http("HTTP协议"),
    /**
     * MQTT协议
     */
    Mqtt("MQTT协议");
    
    private final String description;
    
    OtaPackageDownloadProtocol(String description) {
        this.description = description;
    }
}
