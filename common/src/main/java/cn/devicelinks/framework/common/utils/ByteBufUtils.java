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

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * {@link ByteUtils}数据操作工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ByteBufUtils {
    /**
     * 从{@link ByteBuf}中读取指定长度的字节
     *
     * @param byteBuf    {@link ByteBuf}
     * @param readLength 读取字节长度
     * @return 字节数组
     */
    public static byte[] readBytes(ByteBuf byteBuf, int readLength) {
        byte[] bytes = new byte[readLength];
        for (int i = 0; i < readLength; i++) {
            bytes[i] = byteBuf.readByte();
        }
        return bytes;
    }

    /**
     * 读取指定长度的字节并转换为字符串（字符串去除空格）
     *
     * @param byteBuf    {@link ByteBuf}
     * @param readLength 读取字节长度
     * @return 去空格后的字符串
     */
    public static String readBytesToString(ByteBuf byteBuf, int readLength) {
        return new String(readBytes(byteBuf, readLength), Charset.defaultCharset());
    }

    /**
     * 读取字符串
     *
     * @param buf        {@link ByteBuf}
     * @param readLength 读取长度
     * @param charset    字符集
     * @return 去空格后的字符串
     */
    public static String readString(ByteBuf buf, int readLength, Charset charset) {
        return buf.readCharSequence(readLength, charset).toString();
    }
}
