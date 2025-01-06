package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DeviceAttributeLatest;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceAttributeLatest} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceAttributeLatest extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceAttributeLatest DEVICE_ATTRIBUTE_LATEST = new TDeviceAttributeLatest("device_attribute_latest");
    private TDeviceAttributeLatest(String tableName) {
        super(tableName);
    }
    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column PROPERTIES_ID = Column.withName("properties_id").build();
    public final Column PROPERTIES_VALUE = Column.withName("properties_value").build();
    public final Column LATEST_TIME = Column.withName("latest_time").localDateTimeValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, PROPERTIES_ID, PROPERTIES_VALUE, LATEST_TIME, CREATE_TIME);
    }
}
