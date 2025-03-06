package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.DeviceShadowStatus;
import cn.devicelinks.framework.common.pojos.DeviceShadow;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
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
    public final Column SHADOW_DATA = Column.withName("shadow_data").typeMapper(ColumnValueMappers.DEVICE_SHADOW_DATA_LIST).build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.DEVICE_SHADOW_STATUS).defaultValue(() -> DeviceShadowStatus.Consistency).build();
    public final Column LAST_UPDATE_TIMESTAMP = Column.withName("last_update_timestamp").localDateTimeValue().build();
    public final Column LAST_SYNC_TIMESTAMP = Column.withName("last_sync_timestamp").localDateTimeValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, SHADOW_DATA, STATUS, LAST_UPDATE_TIMESTAMP, LAST_SYNC_TIMESTAMP, CREATE_TIME);
    }
}
