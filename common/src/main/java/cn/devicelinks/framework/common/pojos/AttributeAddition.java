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

package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据属性附加信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    /**
     * 布尔值True映射Key
     */
    private static final String TRUE_VALUE = "1";
    /**
     * 布尔值False映射Key
     */
    private static final String FALSE_VALUE = "0";
    /**
     * 数据属性单位ID
     */
    private String unitId;
    /**
     * 数据步长
     */
    private int step;
    /**
     * 数据长度
     */
    private int dataLength;
    /**
     * 数据范围
     */
    private ValueRange valueRange;
    /**
     * 数据值映射
     */
    private Map<String, String> valueMap = new HashMap<>();

    @Setter
    @Getter
    public static class ValueRange {
        private int min;
        private int max;

    }
}
