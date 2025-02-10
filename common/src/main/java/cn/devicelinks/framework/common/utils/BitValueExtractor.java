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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 位值提取器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BitValueExtractor {

    public static Map<String, String> extract(int[] bitValues, List<BitValue> bitValueDefines) {
        Map<String, String> bitValueMap = new HashMap<>();
        Map<Integer, BitValue> bitValueDefineMap = bitValueDefines.stream().collect(Collectors.toMap(BitValue::getStartBitIndex, v -> v));
        for (int bitIndex = Constants.ZERO; bitIndex < bitValues.length; bitIndex++) {
            int bitValue = bitValues[bitIndex];
            BitValue bitValueDefine = bitValueDefineMap.get(bitIndex);
            if (bitValueDefine != null) {
                // 连续多位的状态
                if (bitValueDefine.isMultiple()) {
                    StringBuilder multipleBitString = new StringBuilder();
                    for (int i = 0; i < (bitValueDefine.getEndBitIndex() - bitValueDefine.getStartBitIndex()) + Constants.ONE; i++) {
                        multipleBitString.append(bitValues[bitValueDefine.getStartBitIndex() + i]);
                    }
                    bitValueMap.put(bitValueDefine.getBitValueName(), multipleBitString.toString());
                }
                // 单位状态
                else {
                    bitValueMap.put(bitValueDefine.getBitValueName(), String.valueOf(bitValue));
                }
            }
            // 预留位
            else {
                bitValueMap.put(BitValue.formatSingleReservedName(bitIndex), String.valueOf(bitValue));
            }
        }
        return bitValueMap;
    }
}
