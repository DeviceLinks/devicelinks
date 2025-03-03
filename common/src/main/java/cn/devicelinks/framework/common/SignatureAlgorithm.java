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
 * 签名算法枚举
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum SignatureAlgorithm {
    SHA1withRSA("SHA1 with RSA"),
    SHA1withDSA("SHA1 with DSA"),
    SHA256withRSA("SHA256 with RSA"),
    SHA256withDSA("SHA256 with DSA"),
    SHA512withDSA("SHA512 with DSA"),
    MD5withRSA("MD5 with RSA"),
    ;
    private final String description;

    SignatureAlgorithm(String description) {
        this.description = description;
    }
}
