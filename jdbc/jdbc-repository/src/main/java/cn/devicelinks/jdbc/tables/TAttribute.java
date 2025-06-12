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

package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link Attribute} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TAttribute extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TAttribute ATTRIBUTE = new TAttribute("attribute");

    private TAttribute(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column PID = Column.withName("pid").build();
    public final Column NAME = Column.withName("name").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column DATA_TYPE = Column.withName("data_type").typeMapper(ColumnValueMappers.ATTRIBUTE_DATA_TYPE).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.ATTRIBUTE_ADDITION).build();
    public final Column WRITABLE = Column.withName("writable").booleanValue().defaultValue(() -> Boolean.TRUE).build();
    public final Column SYSTEM = Column.withName("`system`").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().defaultValue(() -> Boolean.TRUE).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
    public final Column DESCRIPTION = Column.withName("description").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, PRODUCT_ID, MODULE_ID, PID, NAME, IDENTIFIER, DATA_TYPE, ADDITION, WRITABLE, SYSTEM, ENABLED, DELETED, CREATE_BY, CREATE_TIME, DESCRIPTION);
    }
}
