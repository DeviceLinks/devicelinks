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

package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.AttributeDesired;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link AttributeDesired} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TAttributeDesired extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TAttributeDesired ATTRIBUTE_DESIRED = new TAttributeDesired("attribute_desired");

    private TAttributeDesired(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column ATTRIBUTE_ID = Column.withName("attribute_id").build();
    public final Column ATTRIBUTE_VALUE = Column.withName("attribute_value").build();
    public final Column VERSION = Column.withName("version").intValue().build();
    public final Column PULLED = Column.withName("pulled").booleanValue().build();
    public final Column PULL_TIME = Column.withName("pull_time").localDateTimeValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, ATTRIBUTE_ID, ATTRIBUTE_VALUE, VERSION, PULLED, PULL_TIME, CREATE_BY, CREATE_TIME);
    }
}
