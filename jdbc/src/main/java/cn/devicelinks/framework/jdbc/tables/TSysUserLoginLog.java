/*
 *   Copyright (C) 2024  恒宇少年
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
import cn.devicelinks.framework.common.pojos.SysUserLoginLog;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysUserLoginLog} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysUserLoginLog extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysUserLoginLog SYS_USER_LOGIN_LOG = new TSysUserLoginLog("sys_user_login_log");
    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column USER_ID = Column.withName("user_id").build();
    public final Column USER_SESSION_ID = Column.withName("user_session_id").build();
    public final Column REQUEST_ID = Column.withName("request_id").build();
    public final Column IP_ADDRESS = Column.withName("ip_address").build();
    public final Column SOURCE_ADDRESS = Column.withName("source_address").build();
    public final Column PLATFORM_TYPE = Column.withName("platform_type").typeMapper(ColumnValueMappers.PLATFORM_TYPE).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column LOGIN_TIME = Column.withName("login_time").localDateTimeValue().build();

    private TSysUserLoginLog(String tableName) {
        super(tableName);
    }

    @Override
    public List<Column> getColumns() {
        return List.of(ID, USER_ID, USER_SESSION_ID, REQUEST_ID, IP_ADDRESS, SOURCE_ADDRESS, PLATFORM_TYPE, ADDITION, LOGIN_TIME);
    }
}
