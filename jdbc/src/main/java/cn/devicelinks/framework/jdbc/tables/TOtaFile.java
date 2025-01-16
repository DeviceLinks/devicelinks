package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.OtaFile;

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