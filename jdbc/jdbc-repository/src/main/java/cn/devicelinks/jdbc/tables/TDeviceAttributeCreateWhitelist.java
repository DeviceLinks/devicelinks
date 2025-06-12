package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.DeviceAttributeCreateWhitelist;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DeviceAttributeCreateWhitelist} Table impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceAttributeCreateWhitelist extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDeviceAttributeCreateWhitelist DEVICE_ATTRIBUTE_CREATE_WHITELIST = new TDeviceAttributeCreateWhitelist("device_attribute_create_whitelist");

    private TDeviceAttributeCreateWhitelist(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, PRODUCT_ID, MODULE_ID, IDENTIFIER, DELETED, CREATE_BY, CREATE_TIME);
    }
}
