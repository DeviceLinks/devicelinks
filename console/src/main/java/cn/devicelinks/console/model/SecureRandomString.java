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

package cn.devicelinks.console.model;

import java.security.SecureRandom;

/**
 * 随机密码字符串生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SecureRandomString {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
    private static final int ACTIVATE_TOKEN_LENGTH = 50;

    public static String getRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(length);
        for (byte aByte : bytes) {
            sb.append(CHARS.charAt(Math.abs(aByte) % CHARS.length()));
        }
        return sb.toString();
    }

    public static String getActiveToken() {
        return getRandomString(ACTIVATE_TOKEN_LENGTH);
    }
}
