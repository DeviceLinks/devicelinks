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
import cn.devicelinks.entity.OtaFile;

import java.io.Serial;
import java.util.List;

/**
 * The {@link OtaFile} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOtaFile extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TOtaFile OTA_FILE = new TOtaFile("ota_file");

	private TOtaFile(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column OTA_ID = Column.withName("ota_id").build();
	public final Column FILE_SOURCE = Column.withName("file_source").typeMapper(ColumnValueMappers.OTA_FILE_SOURCE).build();
	public final Column FILE_CHECKSUM = Column.withName("file_checksum").build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.OTA_FILE_ADDITION).build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, OTA_ID, FILE_SOURCE, FILE_CHECKSUM, ADDITION, DELETED, CREATE_TIME);
    }
}