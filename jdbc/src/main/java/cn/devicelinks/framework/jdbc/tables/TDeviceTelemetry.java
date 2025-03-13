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
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceTelemetry} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceTelemetry extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceTelemetry DEVICE_TELEMETRY = new TDeviceTelemetry("device_telemetry");

    private TDeviceTelemetry(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column METRIC_TYPE = Column.withName("metric_type").typeMapper(ColumnValueMappers.TELEMETRY_METRIC_TYPE).build();
    public final Column METRIC_KEY = Column.withName("metric_key").build();
    public final Column METRIC_VALUE = Column.withName("metric_value").typeMapper(ColumnValueMappers.JSON_OBJECT).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.TELEMETRY_ADDITION).build();
    public final Column LAST_UPDATE_TIMESTAMP = Column.withName("last_update_timestamp").longValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, METRIC_TYPE, METRIC_KEY, METRIC_VALUE, ADDITION, LAST_UPDATE_TIMESTAMP, DELETED, CREATE_TIME);
    }
}