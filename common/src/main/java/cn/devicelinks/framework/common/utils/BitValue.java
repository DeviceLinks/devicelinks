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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

/**
 * 位值接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BitValue {
    public static final String SINGLE_BIT_VALUE_NAME_FORMAT = "[bit%d]";
    public static final String MULTIPLE_BIT_VALUE_NAME_FORMAT = "[bit%d-%d]";
    /**
     * 位值名称
     */
    @Setter
    private String bitValueName;
    /**
     * 位开始索引
     */
    private int startBitIndex;
    /**
     * 位截止索引
     */
    private int endBitIndex;
    /**
     * 是否为多位值
     */
    private boolean multiple;

    private BitValue(int startBitIndex, int endBitIndex, boolean multiple) {
        this.startBitIndex = startBitIndex;
        this.endBitIndex = endBitIndex;
        this.multiple = multiple;
    }

    public static BitValue instance(int startBitIndex) {
        return new BitValue(startBitIndex, startBitIndex, false);
    }

    public static BitValue instance(int startBitIndex, int endBitIndex) {
        return new BitValue(startBitIndex, endBitIndex, true);
    }

    public String getBitValueName() {
        if (!ObjectUtils.isEmpty(this.bitValueName)) {
            return this.bitValueName;
        }
        // @formatter:off
        return this.multiple ? String.format(MULTIPLE_BIT_VALUE_NAME_FORMAT, this.startBitIndex, this.endBitIndex) :
                String.format(SINGLE_BIT_VALUE_NAME_FORMAT, this.startBitIndex);
        // @formatter:on
    }

    public static String formatSingleReservedName(int bitIndex) {
        return String.format(SINGLE_BIT_VALUE_NAME_FORMAT, bitIndex);
    }
}
