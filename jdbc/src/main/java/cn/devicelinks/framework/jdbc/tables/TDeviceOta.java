package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.common.pojos.DeviceOta;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceOta} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceOta extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TDeviceOta DEVICE_OTA = new TDeviceOta("device_ota");

	private TDeviceOta(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEVICE_ID = Column.withName("device_id").build();
	public final Column MODULE_ID = Column.withName("module_id").build();
	public final Column LATEST_VERSION = Column.withName("latest_version").build();
	public final Column LATEST_REPORT_TIME = Column.withName("latest_report_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, LATEST_VERSION, LATEST_REPORT_TIME);
    }
}