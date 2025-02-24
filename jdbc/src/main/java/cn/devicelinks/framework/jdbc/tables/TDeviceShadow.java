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
    public final Column SHADOW_DOCUMENT = Column.withName("shadow_document").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column VERSION = Column.withName("version").longValue().build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.DEVICE_SHADOW_STATUS).build();
    public final Column LAST_UPDATE_TIMESTAMP = Column.withName("last_update_timestamp").timestamp().build();
    public final Column LAST_PULL_TIME = Column.withName("last_pull_time").timestamp().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, SHADOW_DOCUMENT, VERSION, STATUS, LAST_UPDATE_TIMESTAMP, LAST_PULL_TIME, CREATE_BY, CREATE_TIME);
    }
}
