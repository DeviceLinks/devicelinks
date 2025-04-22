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
import cn.devicelinks.entity.OtaUpgradeStrategy;

import java.io.Serial;
import java.util.List;

/**
 * The {@link OtaUpgradeStrategy} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOtaUpgradeStrategy extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TOtaUpgradeStrategy OTA_UPGRADE_STRATEGY = new TOtaUpgradeStrategy("ota_upgrade_strategy");

	private TOtaUpgradeStrategy(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column OTA_BATCH_ID = Column.withName("ota_batch_id").build();
	public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.OTA_UPGRADE_STRATEGY_TYPE).build();
	public final Column ACTIVE_PUSH = Column.withName("active_push").booleanValue().build();
	public final Column CONFIRM_UPGRADE = Column.withName("confirm_upgrade").booleanValue().build();
	public final Column RETRY_INTERVAL = Column.withName("retry_interval").typeMapper(ColumnValueMappers.OTA_UPGRADE_STRATEGY_RETRY_INTERVAL).build();
	public final Column DOWNLOAD_PROTOCOL = Column.withName("download_protocol").typeMapper(ColumnValueMappers.OTA_PACKAGE_DOWNLOAD_PROTOCOL).build();
	public final Column MULTIPLE_MODULE_UPGRADE = Column.withName("multiple_module_upgrade").booleanValue().build();
	public final Column COVER_BEFORE_UPGRADE = Column.withName("cover_before_upgrade").booleanValue().build();
	public final Column TAGS = Column.withName("tags").typeMapper(ColumnValueMappers.JSON_MAP).build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, OTA_BATCH_ID, TYPE, ACTIVE_PUSH, CONFIRM_UPGRADE, RETRY_INTERVAL, DOWNLOAD_PROTOCOL, MULTIPLE_MODULE_UPGRADE, COVER_BEFORE_UPGRADE, TAGS, CREATE_BY, CREATE_TIME);
    }
}