package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.Constants;
import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceAttribute} Table impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceAttribute extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceAttribute DEVICE_ATTRIBUTE = new TDeviceAttribute("device_attribute");

    private TDeviceAttribute(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column ATTRIBUTE_ID = Column.withName("attribute_id").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column VALUE = Column.withName("value").typeMapper(ColumnValueMappers.JSON_OBJECT).build();
    public final Column VALUE_SOURCE = Column.withName("value_source").typeMapper(ColumnValueMappers.ATTRIBUTE_VALUE_SOURCE).build();
    public final Column VERSION = Column.withName("version").intValue().defaultValue(() -> Constants.ZERO).build();
    public final Column LAST_UPDATE_TIME = Column.withName("last_update_time").localDateTimeValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, MODULE_ID, ATTRIBUTE_ID, IDENTIFIER, VALUE, VALUE_SOURCE, VERSION, LAST_UPDATE_TIME, CREATE_TIME);
    }
}
