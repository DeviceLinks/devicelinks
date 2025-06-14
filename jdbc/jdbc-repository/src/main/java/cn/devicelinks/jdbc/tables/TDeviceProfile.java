package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceProfile} table implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceProfile extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    public static final TDeviceProfile DEVICE_PROFILE = new TDeviceProfile("device_profile");

    private TDeviceProfile(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column DEFAULT_PROFILE = Column.withName("default_profile").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column FIRMWARE_ID = Column.withName("firmware_id").build();
    public final Column SOFTWARE_ID = Column.withName("software_id").build();
    public final Column LOG_ADDITION = Column.withName("log_addition").typeMapper(ColumnValueMappers.DEVICE_PROFILE_LOG_ADDITION).build();
    public final Column ALARM_ADDITION = Column.withName("alarm_addition").typeMapper(ColumnValueMappers.DEVICE_PROFILE_ALARM_ADDITION).build();
    public final Column PROVISION_ADDITION = Column.withName("provision_addition").typeMapper(ColumnValueMappers.DEVICE_PROFILE_PROVISION_ADDITION).build();
    public final Column EXTENSION = Column.withName("extension").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column DESCRIPTION = Column.withName("description").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, DEFAULT_PROFILE, PRODUCT_ID, FIRMWARE_ID, SOFTWARE_ID, LOG_ADDITION, ALARM_ADDITION, PROVISION_ADDITION, EXTENSION, CREATE_BY, CREATE_TIME, DELETED, DESCRIPTION);
    }
}
