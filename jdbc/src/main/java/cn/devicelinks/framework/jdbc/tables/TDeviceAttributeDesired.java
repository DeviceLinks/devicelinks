package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceAttributeDesired} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceAttributeDesired extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceAttributeDesired DEVICE_ATTRIBUTE_DESIRED = new TDeviceAttributeDesired("device_attribute_desired");

    private TDeviceAttributeDesired(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column PROPERTIES_ID = Column.withName("properties_id").build();
    public final Column PROPERTIES_VALUE = Column.withName("properties_value").build();
    public final Column VERSION = Column.withName("version").intValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, PROPERTIES_ID, PROPERTIES_VALUE, VERSION, CREATE_BY, CREATE_TIME);
    }
}
