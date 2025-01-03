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

import cn.devicelinks.framework.common.Constants;

/**
 * 字节{@link Byte}操作工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ByteUtils {
    /**
     * 计算异或和
     *
     * @param bytes 等待校验的byte数组
     * @return 异或和
     */
    public static byte calculateCheckSum(byte[] bytes) {
        byte checkSum = 0;
        for (byte b : bytes) {
            checkSum ^= b;
        }
        return checkSum;
    }

    /**
     * 复制byte数组
     * 从指定位置开始，复制连续长度的byte值填充到新数组
     *
     * @param bytes  原始byte数组
     * @param start  开始位置
     * @param length 连续读取的长度
     * @return 读取后的byte数组
     */
    public static byte[] copyBytes(byte[] bytes, int start, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(bytes, start, temp, Constants.ZERO, length);
        return temp;
    }

    public static void mergeBytes(byte[] srcBytes, int srcStart, byte[] targetBytes, int targetStart) {
        System.arraycopy(srcBytes, srcStart, targetBytes, targetStart, srcBytes.length);
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * byte转Hex字符串
     *
     * @param b byte
     * @return hex字符串
     */
    public static String byteToHex(byte b) {
        return Integer.toHexString(b);
    }
}
