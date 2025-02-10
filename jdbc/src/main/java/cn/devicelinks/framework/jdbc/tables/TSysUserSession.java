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
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.SysUserSession;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysUserSession} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysUserSession extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TSysUserSession SYS_USER_SESSION = new TSysUserSession("sys_user_session");

	private TSysUserSession(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column USER_ID = Column.withName("user_id").build();
	public final Column USERNAME = Column.withName("username").build();
	public final Column TOKEN_VALUE = Column.withName("token_value").build();
	public final Column PLATFORM_TYPE = Column.withName("platform_type").typeMapper(ColumnValueMappers.PLATFORM_TYPE).build();
	public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.SESSION_STATUS).build();
	public final Column ISSUED_TIME = Column.withName("issued_time").localDateTimeValue().build();
	public final Column EXPIRES_TIME = Column.withName("expires_time").localDateTimeValue().build();
	public final Column LOGOUT_TIME = Column.withName("logout_time").localDateTimeValue().build();
	public final Column LAST_ACTIVE_TIME = Column.withName("last_active_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, USER_ID, USERNAME, TOKEN_VALUE, PLATFORM_TYPE, STATUS, ISSUED_TIME, EXPIRES_TIME, LOGOUT_TIME, LAST_ACTIVE_TIME);
    }
}