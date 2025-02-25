package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DeviceShadowHistory;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceShadowHistory} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceShadowHistory extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceShadowHistory DEVICE_SHADOW_HISTORY = new TDeviceShadowHistory("device_shadow_history");

    private TDeviceShadowHistory(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column SHADOW_NAME = Column.withName("shadow_name").build();
    public final Column OPERATION_TYPE = Column.withName("operation_type").typeMapper(ColumnValueMappers.DEVICE_SHADOW_HISTORY_OPERATION_TYPE).build();
    public final Column PREVIOUS_VERSION = Column.withName("previous_version").longValue().build();
    public final Column CURRENT_VERSION = Column.withName("current_version").longValue().build();
    public final Column SHADOW_DATA = Column.withName("shadow_data").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column DELTA = Column.withName("delta").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column OPERATION_TIMESTAMP = Column.withName("operation_timestamp").timestamp().build();
    public final Column OPERATION_SOURCE = Column.withName("operation_source").typeMapper(ColumnValueMappers.DEVICE_SHADOW_HISTORY_OPERATION_SOURCE).build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, SHADOW_NAME, OPERATION_TYPE, PREVIOUS_VERSION, CURRENT_VERSION, SHADOW_DATA, DELTA, OPERATION_TIMESTAMP, OPERATION_SOURCE, CREATE_TIME);
    }
}
