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
import cn.devicelinks.framework.common.pojos.SysGlobalSetting;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysGlobalSetting} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysGlobalSetting extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TSysGlobalSetting SYS_GLOBAL_SETTING = new TSysGlobalSetting("sys_global_setting");

	private TSysGlobalSetting(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column FLAG = Column.withName("flag").build();
	public final Column DEFAULT_VALUE = Column.withName("default_value").build();
	public final Column DATA_TYPE = Column.withName("data_type").typeMapper(ColumnValueMappers.GLOBAL_SETTING_DATA_TYPE).build();
	public final Column MULTIVALUED = Column.withName("multivalued").booleanValue().build();
	public final Column ALLOW_SELF_SET = Column.withName("allow_self_set").booleanValue().build();
	public final Column ENABLED = Column.withName("enabled").booleanValue().build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
	public final Column MARK = Column.withName("mark").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, FLAG, DEFAULT_VALUE, DATA_TYPE, MULTIVALUED, ALLOW_SELF_SET, ENABLED, CREATE_TIME, MARK);
    }
}