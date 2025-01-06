package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DeviceOtaLatest;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceOtaLatest} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceOtaLatest extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceOtaLatest DEVICE_OTA_LATEST = new TDeviceOtaLatest("device_ota_latest");

    private TDeviceOtaLatest(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column MODULE = Column.withName("module").build();
    public final Column VERSION = Column.withName("version").build();
    public final Column LAST_REPORT_TIME = Column.withName("last_report_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE, VERSION, LAST_REPORT_TIME);
    }
}
