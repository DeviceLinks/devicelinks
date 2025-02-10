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

import cn.devicelinks.framework.common.Constants;

import java.util.BitSet;

/**
 * 操作位的工具
 */
public class Bit {
    public static final int BYTE_MAX_BIT_COUNT = 8;
    public static final int SHORT_MAX_BIT_COUNT = 16;
    public static final int INT_MAX_BIT_COUNT = 32;


    /**
     * 将 byte 转为二进制 String
     *
     * @param by   需要转换byte
     * @param high 起始位
     * @param low  结束为
     * @return 转换后的字符串 例如: byte(11,7,0) --> 00001011
     */
    public static String byteToBinaryStr(byte by, int high, int low) {
        byte[] arr = {by};
        var bitSet = BitSet.valueOf(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = high; i >= low; i--) {
            sb.append(bitSet.get(i) ? Constants.ONE : Constants.ZERO);
        }
        return sb.toString();
    }

    /**
     * 获取整型指定位布尔类型的值
     *
     * @param val   整型变量
     * @param index 位索引
     * @return 位值，0或者1
     */
    public static boolean getBitBooleanValue(int val, int index) {
        return getBitValue(val, index) != Constants.ZERO;
    }

    /**
     * 获取整型指定位的值
     *
     * @param val   整型变量
     * @param index 位索引
     * @return 位值，0或者1
     */
    public static int getBitValue(int val, int index) {
        return ((val >> index) & Constants.ONE);
    }

    /**
     * 获取byte变量各个位的值
     *
     * @param val 整型变量
     * @return 整型变量各个位的值，每个位值为0或者1
     */
    public static int[] getByteBitValues(byte val) {
        return Bit.getIntegerBitValues(val, BYTE_MAX_BIT_COUNT);
    }

    /**
     * 获取short变量各个位的值
     *
     * @param val 整型变量
     * @return 整型变量各个位的值，每个位值为0或者1
     */
    public static int[] getShortBitValues(short val) {
        return Bit.getIntegerBitValues(val, SHORT_MAX_BIT_COUNT);
    }

    /**
     * 获取int变量各个位的值
     *
     * @param val 整型变量
     * @return 整型变量各个位的值，每个位值为0或者1
     */
    public static int[] getIntBitValues(int val) {
        return Bit.getIntegerBitValues(val, INT_MAX_BIT_COUNT);
    }

    /**
     * 获取整型变量各个位的值
     *
     * @param val 整型变量
     * @return 整型变量各个位的值，每个位值为0或者1
     */
    public static int[] getIntegerBitValues(int val, int maxBitCount) {
        int[] bitValues = new int[maxBitCount];
        for (int i = maxBitCount - Constants.ONE; i >= Constants.ZERO; i--) {
            bitValues[i] = Bit.getBitValue(val, i);
        }
        return bitValues;
    }
}
