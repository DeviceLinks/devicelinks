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
import cn.devicelinks.entity.NotificationReceiver;

import java.io.Serial;
import java.util.List;

/**
 * The {@link NotificationReceiver} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TNotificationReceiver extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TNotificationReceiver NOTIFICATION_RECEIVER = new TNotificationReceiver("notification_receiver");

	private TNotificationReceiver(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.NOTIFICATION_TYPE).build();
	public final Column MATCH_USER_AWAY = Column.withName("match_user_away").typeMapper(ColumnValueMappers.NOTIFICATION_MATCH_USER_AWAY).build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
	public final Column DESCRIPTION = Column.withName("description").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, TYPE, MATCH_USER_AWAY, CREATE_BY, CREATE_TIME, DESCRIPTION);
    }
}