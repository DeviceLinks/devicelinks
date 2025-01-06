package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.OtaUpgradeBatch;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link OtaUpgradeBatch} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOtaUpgradeBatch extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TOtaUpgradeBatch OTA_UPGRADE_BATCH = new TOtaUpgradeBatch("ota_upgrade_batch");

    private TOtaUpgradeBatch(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column OTA_ID = Column.withName("ota_id").build();
    public final Column NAME = Column.withName("name").build();
    public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_TYPE).build();
    public final Column STATE = Column.withName("state").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_STATE).build();
    public final Column UPGRADE_METHOD = Column.withName("upgrade_method").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_METHOD).build();
    public final Column UPGRADE_SCOPE = Column.withName("upgrade_scope").typeMapper(ColumnValueMappers.OTA_UPGRADE_BATCH_SCOPE).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
    public final Column MARK = Column.withName("mark").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, OTA_ID, NAME, TYPE, STATE, UPGRADE_METHOD, UPGRADE_SCOPE, ADDITION, CREATE_BY, CREATE_TIME, MARK);
    }
}
