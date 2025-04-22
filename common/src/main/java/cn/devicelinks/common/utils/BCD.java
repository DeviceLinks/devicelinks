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

package cn.devicelinks.common.utils;


import io.netty.buffer.ByteBuf;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

/**
 * BCD工具类
 *
 * @author 恒宇少年
 */
public class BCD {
    /**
     * 总缓冲区中读 6 个字节，并将其转换为时间<br>
     * 日期格式：01:02:03:04:05:06 会被转换为 2006-05-04 01:02:03
     *
     * @param in 被读取数据的缓冲区
     * @return 读取到的时间，时间为 UTC 时间
     */
    public static LocalDateTime bcd2DateTime_HHmmssddMMyyyy(@NonNull ByteBuf in) {
        int th = bcd2int(in, 1);
        int tm = bcd2int(in, 1);
        int ts = bcd2int(in, 1);
        int dd = bcd2int(in, 1);
        int dm = bcd2int(in, 1);
        int dy = 2000 + bcd2int(in, 1);

        // UTC 时间
        return LocalDateTime.of(dy, dm, dd, th, tm, ts);
    }

    /**
     * 总缓冲区中读 6 个字节，并将其转换为时间<br>
     * 日期格式：01:02:03:04:05:06 会被转换为 2001-02-03 04:05:06
     *
     * @param in 被读取数据的缓冲区
     * @return 读取到的时间，时间为 UTC 时间
     */
    public static LocalDateTime bcd2DateTime_yyyyMMddHHmmss(@NonNull ByteBuf in) {
        int dy = 2000 + bcd2int(in, 1);
        int dm = bcd2int(in, 1);
        int dd = bcd2int(in, 1);
        int th = bcd2int(in, 1);
        int tm = bcd2int(in, 1);
        int ts = bcd2int(in, 1);

        if (dy == 0 || dm == 0 || dd == 0) {
            return LocalDateTime.now(); // 若无法正常解析，则给当前时间
        }

        // UTC 时间
        return LocalDateTime.of(dy, dm, dd, th, tm, ts);
    }

    /**
     * 从缓冲区中读指定长度的 BCD 数，并将其转换为 String
     * <p>转换时，会保留前导 0</p>
     *
     * @param in  被读取数据的缓冲区
     * @param len 要读取的长度
     * @return 读取到的 BCD 数
     */
    @NonNull
    public static String bcd2str(@NonNull ByteBuf in, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int num = bcd2int(in.readByte());
            if (num < 10) {
                sb.append('0');
            }
            sb.append(num);
        }

        return sb.toString();
    }

    /**
     * 将 String 的 BCD 数转换为 byte 数组 (BCD)
     *
     * @param str 被转换的字符串
     * @return 转换结果
     */
    @NonNull
    public static byte[] str2bcd(@NonNull String str) {
        if (str.length() == 0) {
            return new byte[]{};
        }
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException("str.length() 必须为 2 的整数倍");
        }

        byte[] buf = new byte[str.length() / 2];
        for (int i = 0, n = 0; i < str.length(); i += 2, n += 1) {
            int h = str.charAt(i) - '0';
            int l = str.charAt(i + 1) - '0';

            buf[n] = (byte) ((h << 4 | l) & 0xFF);
        }

        return buf;
    }

    /**
     * 从缓冲区中读指定长度的 BCD 数，并将其转换为 int
     *
     * @param in  被读取数据的缓冲区
     * @param len 要读取的长度，最大为 5<br>
     *            需要注意：当长度为 5 时，读取到的数据可能会超过 Integer.MAX_VALUE，这种情况下会导致抛异常
     * @return 读取到的 BCD 数
     */
    public static int bcd2int(@NonNull ByteBuf in, int len) {
        if (len > 5) { // max = 2147483647
            throw new IllegalArgumentException("len > 5");
        }

        long sum = 0;
        for (int i = 0; i < len; i++) {
            int num = bcd2int(in.readByte());
            sum *= 100;
            sum += num;
        }

        if (sum > Integer.MAX_VALUE) {
            throw new IllegalStateException("num > 2147483647");
        }

        return (int) sum;
    }

    /**
     * 将一个 byte 转换为 BCD 数
     *
     * @param bcd 被转换的 byte
     * @return 转换后的 BCD 数
     */
    public static int bcd2int(byte bcd) {
        int h = (bcd >> 4) & 0x0F;
        int l = bcd & 0x0F;
        return h * 10 + l;
    }
}
