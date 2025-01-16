package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceTelemetry} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceTelemetry extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TDeviceTelemetry DEVICE_TELEMETRY = new TDeviceTelemetry("device_telemetry");

	private TDeviceTelemetry(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEVICE_ID = Column.withName("device_id").build();
	public final Column ATTRIBUTE_ID = Column.withName("attribute_id").build();
	public final Column KEY = Column.withName("key").build();
	public final Column LATEST_VALUE = Column.withName("latest_value").build();
	public final Column LATEST_REPORT_TIME = Column.withName("latest_report_time").localDateTimeValue().build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, ATTRIBUTE_ID, KEY, LATEST_VALUE, LATEST_REPORT_TIME, CREATE_TIME);
    }
}