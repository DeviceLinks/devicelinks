package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.OtaUpgradeStrategy;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link OtaUpgradeStrategy} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOtaUpgradeStrategy extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TOtaUpgradeStrategy OTA_UPGRADE_STRATEGY = new TOtaUpgradeStrategy("ota_upgrade_strategy");

    private TOtaUpgradeStrategy(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column OTA_BATCH_ID = Column.withName("ota_batch_id").build();
    public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.OTA_UPGRADE_STRATEGY_TYPE).build();
    public final Column ACTIVE_PUSH = Column.withName("active_push").booleanValue().build();
    public final Column CONFIRM_UPGRADE = Column.withName("confirm_upgrade").booleanValue().build();
    public final Column RETRY_INTERVAL = Column.withName("retry_interval").typeMapper(ColumnValueMappers.OTA_UPGRADE_STRATEGY_RETRY_INTERVAL).build();
    public final Column DOWNLOAD_PROTOCOL = Column.withName("download_protocol").typeMapper(ColumnValueMappers.OTA_PACKAGE_DOWNLOAD_PROTOCOL).build();
    public final Column MULTIPLE_MODULE_UPGRADE = Column.withName("multiple_module_upgrade").booleanValue().build();
    public final Column COVER_BEFORE_UPGRADE = Column.withName("cover_before_upgrade").booleanValue().build();
    public final Column TAGS = Column.withName("tags").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, OTA_BATCH_ID, TYPE, ACTIVE_PUSH, CONFIRM_UPGRADE, RETRY_INTERVAL, DOWNLOAD_PROTOCOL, MULTIPLE_MODULE_UPGRADE, COVER_BEFORE_UPGRADE, TAGS, CREATE_BY, CREATE_TIME);
    }
}