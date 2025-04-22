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
import cn.devicelinks.entity.Notification;

import java.io.Serial;
import java.util.List;

/**
 * The {@link Notification} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TNotification extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TNotification NOTIFICATION = new TNotification("notification");

	private TNotification(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEPARTMENT_ID = Column.withName("department_id").build();
	public final Column TYPE_ID = Column.withName("type_id").build();
	public final Column SUBJECT = Column.withName("subject").build();
	public final Column MESSAGE = Column.withName("message").build();
	public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.NOTIFICATION_STATUS).build();
	public final Column SEVERITY = Column.withName("severity").typeMapper(ColumnValueMappers.NOTIFICATION_SEVERITY).build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.NOTIFICATION_ADDITION).build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEPARTMENT_ID, TYPE_ID, SUBJECT, MESSAGE, STATUS, SEVERITY, ADDITION, CREATE_TIME);
    }
}