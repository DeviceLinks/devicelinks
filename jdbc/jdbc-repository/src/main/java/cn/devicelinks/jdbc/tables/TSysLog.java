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
import cn.devicelinks.entity.SysLog;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link SysLog} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysLog extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysLog SYS_LOG = new TSysLog("sys_log");

    private TSysLog(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column USER_ID = Column.withName("user_id").build();
    public final Column SESSION_ID = Column.withName("session_id").build();
    public final Column ACTION = Column.withName("action").typeMapper(ColumnValueMappers.LOG_ACTION).build();
    public final Column OBJECT_TYPE = Column.withName("object_type").typeMapper(ColumnValueMappers.LOG_OBJECT_TYPE).build();
    public final Column OBJECT_ID = Column.withName("object_id").build();
    public final Column MSG = Column.withName("msg").build();
    public final Column SUCCESS = Column.withName("success").booleanValue().build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.SYS_LOG_ADDITION).build();
    public final Column ACTIVITY_DATA = Column.withName("activity_data").build();
    public final Column OPERATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, USER_ID, SESSION_ID, ACTION, OBJECT_TYPE, OBJECT_ID, MSG, SUCCESS, ADDITION, ACTIVITY_DATA, OPERATE_TIME);
    }
}