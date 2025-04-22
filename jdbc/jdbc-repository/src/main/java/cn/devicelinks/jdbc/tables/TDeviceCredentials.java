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
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.entity.DeviceCredentials;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceCredentials} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceCredentials extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceCredentials DEVICE_CREDENTIALS = new TDeviceCredentials("device_credentials");

    private TDeviceCredentials(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column DEVICE_CREDENTIALS_TYPE = Column.withName("credentials_type").typeMapper(ColumnValueMappers.DEVICE_CREDENTIALS_TYPE).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.DEVICE_AUTHENTICATION_ADDITION).build();
    public final Column EXPIRATION_TIME = Column.withName("expiration_time").localDateTimeValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, DEVICE_CREDENTIALS_TYPE, ADDITION, EXPIRATION_TIME, DELETED, CREATE_TIME);
    }
}