package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.OtaFile;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

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
    public final Column FILE_ID = Column.withName("file_id").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, OTA_ID, FILE_ID, CREATE_TIME);
    }
}
