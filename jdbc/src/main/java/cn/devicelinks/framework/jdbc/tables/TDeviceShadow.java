package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DeviceShadow;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceShadow} table implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceShadow extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceShadow DEVICE_SHADOW = new TDeviceShadow("device_shadow");

    private TDeviceShadow(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column REPORTED_STATE = Column.withName("reported_state").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column DESIRED_STATE = Column.withName("desired_state").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column REPORTED_VERSION = Column.withName("reported_version").longValue().build();
    public final Column DESIRED_VERSION = Column.withName("desired_version").longValue().build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.DEVICE_SHADOW_STATUS).build();
    public final Column LAST_UPDATE_TIMESTAMP = Column.withName("last_update_timestamp").timestamp().build();
    public final Column LAST_SYNC_TIMESTAMP = Column.withName("last_sync_timestamp").timestamp().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, REPORTED_STATE, DESIRED_STATE, REPORTED_VERSION, DESIRED_VERSION, STATUS, LAST_UPDATE_TIMESTAMP, LAST_SYNC_TIMESTAMP, CREATE_TIME);
    }
}
