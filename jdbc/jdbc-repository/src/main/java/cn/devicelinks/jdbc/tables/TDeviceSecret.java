package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.common.DeviceSecretStatus;
import cn.devicelinks.entity.DeviceSecret;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceSecret} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceSecret extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    public static final TDeviceSecret DEVICE_SECRET = new TDeviceSecret("device_secret");

    private TDeviceSecret(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column ENCRYPTED_SECRET = Column.withName("encrypted_secret").build();
    public final Column IV = Column.withName("iv").build();
    public final Column SECRET_VERSION = Column.withName("secret_version").build();
    public final Column SECRET_KEY_VERSION = Column.withName("secret_key_version").build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.DEVICE_SECRET_STATUS).defaultValue(() -> DeviceSecretStatus.Active).build();
    public final Column EXPIRES_TIME = Column.withName("expires_time").localDateTimeValue().build();
    public final Column LAST_USE_TIME = Column.withName("last_use_time").localDateTimeValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, ENCRYPTED_SECRET, IV, SECRET_VERSION, SECRET_KEY_VERSION, STATUS, EXPIRES_TIME, LAST_USE_TIME, CREATE_TIME);
    }
}
