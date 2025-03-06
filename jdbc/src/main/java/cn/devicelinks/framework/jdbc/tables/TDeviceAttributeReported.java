package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DeviceAttributeReported;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceAttributeReported} Table impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceAttributeReported extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceAttributeReported DEVICE_ATTRIBUTE_REPORTED = new TDeviceAttributeReported("device_attribute_reported");

    private TDeviceAttributeReported(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column ATTRIBUTE_ID = Column.withName("attribute_id").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column REPORT_VALUE = Column.withName("report_value").typeMapper(ColumnValueMappers.JSON_OBJECT).build();
    public final Column VERSION = Column.withName("version").intValue().defaultValue(() -> Constants.ZERO).build();
    public final Column LAST_REPORT_TIME = Column.withName("last_report_time").localDateTimeValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, ATTRIBUTE_ID, IDENTIFIER, REPORT_VALUE, VERSION, LAST_REPORT_TIME, CREATE_TIME);
    }
}
