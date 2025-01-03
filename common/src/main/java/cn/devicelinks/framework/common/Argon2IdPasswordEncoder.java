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

package cn.devicelinks.framework.common;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * "Argon2id"密码编码器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class Argon2IdPasswordEncoder {

    private static final int ITERATIONS = 10;

    private static final int MEMORY = 65536;

    private static final int PARALLELISM = 1;

    public static final Argon2Factory.Argon2Types TYPE = Argon2Factory.Argon2Types.ARGON2id;

    private static final Argon2 INSTANCE = Argon2Factory.create(TYPE);

    /**
     * 密码编码
     *
     * @param plainTextPassword 明文密码
     * @return 加密后的密码
     */
    public static String encode(String plainTextPassword) {
        return INSTANCE.hash(ITERATIONS, MEMORY, PARALLELISM, plainTextPassword.toCharArray());
    }

    /**
     * 密码匹配
     *
     * @param encodedPassword   加密后的密码
     * @param plainTextPassword 明文密码
     * @return 返回true时表示匹配
     */
    public static boolean matches(String encodedPassword, String plainTextPassword) {
        return INSTANCE.verify(encodedPassword, plainTextPassword.toCharArray());
    }
}
