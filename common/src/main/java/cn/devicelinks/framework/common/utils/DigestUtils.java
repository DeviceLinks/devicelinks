/*
 *   Copyright (C) 2024  DeviceLinks
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

package cn.devicelinks.framework.common.utils;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Objects;

/**
 * {@link MessageDigest} 工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DigestUtils {
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA512 = "SHA-512";


    public static byte[] getDigest(String algorithm, byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            return digest.digest(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getHexDigest(String algorithm, byte[] data) {
        return HexFormat.of().formatHex(Objects.requireNonNull(getDigest(algorithm, data)));
    }

    public static String getBase64Digest(String algorithm, byte[] data) {
        return Base64.getEncoder().encodeToString(Objects.requireNonNull(getDigest(algorithm, data)));
    }
}
