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
import cn.devicelinks.entity.SysSetting;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link SysSetting} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysSetting extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TSysSetting SYS_SETTING = new TSysSetting("sys_setting");

	private TSysSetting(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column FLAG = Column.withName("flag").build();
	public final Column VALUE = Column.withName("value").build();
	public final Column DATA_TYPE = Column.withName("data_type").typeMapper(ColumnValueMappers.SYS_SETTING_DATA_TYPE).build();
	public final Column MULTIVALUED = Column.withName("multivalued").booleanValue().build();
	public final Column ALLOW_SELF_SET = Column.withName("allow_self_set").booleanValue().build();
	public final Column ENABLED = Column.withName("enabled").booleanValue().build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
	public final Column MARK = Column.withName("mark").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, FLAG, VALUE, DATA_TYPE, MULTIVALUED, ALLOW_SELF_SET, ENABLED, CREATE_TIME, MARK);
    }
}