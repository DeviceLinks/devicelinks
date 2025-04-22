package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.Constants;
import cn.devicelinks.common.DesiredAttributeStatus;
import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.DeviceAttributeDesired;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceAttributeDesired} Table impl
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
    public final Column ATTRIBUTE_ID = Column.withName("attribute_id").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column DATA_TYPE = Column.withName("data_type").typeMapper(ColumnValueMappers.ATTRIBUTE_DATA_TYPE).build();
    public final Column VERSION = Column.withName("version").intValue().defaultValue(() -> Constants.ZERO).build();
    public final Column DESIRED_VALUE = Column.withName("desired_value").typeMapper(ColumnValueMappers.JSON_OBJECT).build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.DESIRED_ATTRIBUTE_STATUS).defaultValue(() -> DesiredAttributeStatus.Pending).build();
    public final Column LAST_UPDATE_TIME = Column.withName("last_update_time").localDateTimeValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
    public final Column EXPIRED_TIME = Column.withName("expired_time").localDateTimeValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, ATTRIBUTE_ID, IDENTIFIER, DATA_TYPE, VERSION, DESIRED_VALUE, STATUS, LAST_UPDATE_TIME, CREATE_TIME, EXPIRED_TIME, DELETED);
    }
}
