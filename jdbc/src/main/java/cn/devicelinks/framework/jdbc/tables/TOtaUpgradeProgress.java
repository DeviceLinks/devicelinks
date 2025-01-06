package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.OtaUpgradeProgress;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

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
