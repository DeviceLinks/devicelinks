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

package cn.devicelinks.jdbc.core.sql;

import cn.devicelinks.jdbc.core.definition.Column;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * 执行SQL时所需要的列与值的封装对象
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ConditionValue {
    private final Column column;
    private final Object value;

    // @formatter:off
    private ConditionValue(Column column, Object value) {
        this.column = column;
        this.value = value;
    }
    // @formatter:on

    public static ConditionValue with(Column column, Object value) {
        Assert.notNull(column, "The filter column cannot be null.");
        return new ConditionValue(column, value);
    }
}
