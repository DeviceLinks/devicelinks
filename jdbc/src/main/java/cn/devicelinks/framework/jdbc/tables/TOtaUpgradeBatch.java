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
import cn.devicelinks.framework.common.pojos.OtaUpgradeBatch;

import java.io.Serial;
import java.util.List;

/**
 * The {@link OtaUpgradeBatch} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOtaUpgradeBatch extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TOtaUpgradeBatch OTA_UPGRADE_BATCH = new TOtaUpgradeBatch("ota_upgrade_batch");

	private TOtaUpgradeBatch(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column OTA_ID = Column.withName("ota_id").build();
	public final Column NAME = Column.withName("name").build();
	public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_TYPE).build();
	public final Column STATE = Column.withName("state").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_STATE).build();
	public final Column UPGRADE_METHOD = Column.withName("upgrade_method").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_METHOD).build();
	public final Column UPGRADE_SCOPE = Column.withName("upgrade_scope").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_SCOPE).build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_ADDITION).build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
	public final Column MARK = Column.withName("mark").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, OTA_ID, NAME, TYPE, STATE, UPGRADE_METHOD, UPGRADE_SCOPE, ADDITION, CREATE_BY, CREATE_TIME, MARK);
    }
}