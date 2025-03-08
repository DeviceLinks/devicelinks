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
import cn.devicelinks.framework.common.DeviceStatus;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.Device;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link Device} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDevice extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDevice DEVICE = new TDevice("device");

    private TDevice(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEPARTMENT_ID = Column.withName("department_id").build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column DEVICE_CODE = Column.withName("device_code").build();
    public final Column DEVICE_TYPE = Column.withName("device_type").typeMapper(ColumnValueMappers.DEVICE_TYPE).build();
    public final Column NAME = Column.withName("name").build();
    public final Column STATUS = Column.withName("status").defaultValue(() -> DeviceStatus.NotActivate).typeMapper(ColumnValueMappers.DEVICE_STATUS).build();
    public final Column TAGS = Column.withName("tags").typeMapper(ColumnValueMappers.STRING_JOINER).build();
    public final Column IP_ADDRESS = Column.withName("ip_address").build();
    public final Column ACTIVATION_TIME = Column.withName("activation_time").localDateTimeValue().build();
    public final Column LAST_ONLINE_TIME = Column.withName("last_online_time").localDateTimeValue().build();
    public final Column LAST_REPORT_TIME = Column.withName("last_report_time").localDateTimeValue().build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().defaultValue(() -> Boolean.TRUE).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.DEVICE_ADDITION).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
    public final Column MARK = Column.withName("mark").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEPARTMENT_ID, PRODUCT_ID, DEVICE_CODE, DEVICE_TYPE, NAME, STATUS, IP_ADDRESS, TAGS, ACTIVATION_TIME, LAST_ONLINE_TIME, LAST_REPORT_TIME, ENABLED, DELETED, ADDITION, CREATE_BY, CREATE_TIME, MARK);
    }
}