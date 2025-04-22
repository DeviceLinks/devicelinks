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
import cn.devicelinks.entity.OtaUpgradeProgress;

import java.io.Serial;
import java.util.List;

/**
 * The {@link OtaUpgradeProgress} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOtaUpgradeProgress extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TOtaUpgradeProgress OTA_UPGRADE_PROGRESS = new TOtaUpgradeProgress("ota_upgrade_progress");

	private TOtaUpgradeProgress(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEVICE_ID = Column.withName("device_id").build();
	public final Column OTA_ID = Column.withName("ota_id").build();
	public final Column OTA_BATCH_ID = Column.withName("ota_batch_id").build();
	public final Column PROGRESS = Column.withName("progress").intValue().build();
	public final Column STATE = Column.withName("state").typeMapper(ColumnValueMappers.OTA_UPGRADE_PROGRESS_STATE).build();
	public final Column STATE_DESC = Column.withName("state_desc").build();
	public final Column START_TIME = Column.withName("start_time").localDateTimeValue().build();
	public final Column COMPLETE_TIME = Column.withName("complete_time").localDateTimeValue().build();
	public final Column FAILURE_REASON = Column.withName("failure_reason").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, OTA_ID, OTA_BATCH_ID, PROGRESS, STATE, STATE_DESC, START_TIME, COMPLETE_TIME, FAILURE_REASON);
    }
}