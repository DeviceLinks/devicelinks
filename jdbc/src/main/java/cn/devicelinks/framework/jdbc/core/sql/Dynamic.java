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

package cn.devicelinks.framework.jdbc.core.sql;

import cn.devicelinks.framework.jdbc.core.definition.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 动态SQL执行实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Dynamic {
    private String sql;
    private boolean modifying;
    private List<Column> resultColumns;
    private Class<?> rowMappingClass;

    public Dynamic(String sql, boolean modifying, List<Column> resultColumns, Class<?> rowMappingClass) {
        this.sql = sql;
        this.modifying = modifying;
        this.resultColumns = resultColumns;
        this.rowMappingClass = rowMappingClass;
    }

    public static Dynamic buildSelect(String sql, List<Column> resultColumns, Class<?> rowMappingClass) {
        Assert.hasText(sql, "dynamic select sql must not be empty");
        Assert.notEmpty(resultColumns, "resultColumns cannot be empty");
        Assert.notNull(rowMappingClass, "rowMappingClass must not be null");
        return new Dynamic(sql, false, resultColumns, rowMappingClass);
    }

    public static Dynamic buildModify(String sql) {
        Assert.hasText(sql, "dynamic modify sql must not be empty");
        return new Dynamic(sql, true, null, null);
    }
}
