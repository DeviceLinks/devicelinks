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
import cn.devicelinks.framework.common.pojos.SysDepartment;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysDepartment} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysDepartment extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TSysDepartment SYS_DEPARTMENT = new TSysDepartment("sys_department");

	private TSysDepartment(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column IDENTIFIER = Column.withName("identifier").build();
	public final Column PID = Column.withName("pid").build();
	public final Column SORT = Column.withName("sort").intValue().build();
	public final Column LEVEL = Column.withName("level").intValue().build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
	public final Column DESCRIPTION = Column.withName("description").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, IDENTIFIER, PID, SORT, LEVEL, DELETED, CREATE_BY, CREATE_TIME, DESCRIPTION);
    }
}