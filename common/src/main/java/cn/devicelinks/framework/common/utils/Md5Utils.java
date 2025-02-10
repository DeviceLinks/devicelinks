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

package cn.devicelinks.framework.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5加密工具类
 * <p>
 * 提供32位、16位大小写加密方法，支持通过加盐字符串来二次格式化
 *
 * @author 恒宇少年
 * @since 1.0.
 */
@Slf4j
public final class Md5Utils {

    public static String getFileMd5(byte[] fileData) {
        return DigestUtils.getHexDigest(DigestUtils.MD5, fileData);
    }

    public static String md5Lower(String plainText) {
        String md5 = null;
        if (null != plainText && !plainText.isEmpty()) {
            try {
                MessageDigest md = MessageDigest.getInstance(DigestUtils.MD5);
                md.update(plainText.getBytes(StandardCharsets.UTF_8));
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return md5;
    }

    public static String md5Lower(String plainText, String saltValue) {
        String md5 = null;
        if (null != plainText && !plainText.isEmpty() && null != saltValue && !saltValue.isEmpty()) {
            try {
                MessageDigest md = MessageDigest.getInstance(DigestUtils.MD5);
                md.update(plainText.getBytes(StandardCharsets.UTF_8));
                md.update(saltValue.getBytes(StandardCharsets.UTF_8));
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return md5;
    }

    public static String md5_16Lower(String plainText) {
        String md5 = md5Lower(plainText);
        return null == md5 ? null : md5.substring(8, 24);
    }

    public static String md5_16Lower(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null == md5 ? null : md5.substring(8, 24);
    }

    public static String md5_16Upper(String plainText) {
        String md5 = md5_16Lower(plainText);
        return null == md5 ? null : md5.toUpperCase();
    }

    public static String md5_16Upper(String plainText, String saltValue) {
        String md5 = md5_16Lower(plainText, saltValue);
        return null == md5 ? null : md5.toUpperCase();
    }

    public static String md5Upper(String plainText) {
        String md5 = md5Lower(plainText);
        return null == md5 ? null : md5.toUpperCase();
    }

    public static String md5Upper(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null == md5 ? null : md5.toUpperCase();
    }
}
