package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link Product} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TProduct extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TProduct PRODUCT = new TProduct("product");

    private TProduct(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column PRODUCT_KEY = Column.withName("product_key").build();
    public final Column PRODUCT_SECRET = Column.withName("product_secret").build();
    public final Column NETWORKING_AWAY = Column.withName("networking_away").typeMapper(ColumnValueMappers.DEVICE_NETWORKING_AWAY).build();
    public final Column DATA_FORMAT = Column.withName("data_format").typeMapper(ColumnValueMappers.DATA_FORMAT).build();
    public final Column AUTHENTICATION_METHOD = Column.withName("authentication_method").typeMapper(ColumnValueMappers.DEVICE_AUTHENTICATION_METHOD).build();
    public final Column SIGNATURE_CODE = Column.withName("signature_code").build();
    public final Column DYNAMIC_REGISTRATION = Column.withName("dynamic_registration").build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.PRODUCT_STATUS).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column DESCRIPTION = Column.withName("description").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, PRODUCT_KEY, PRODUCT_SECRET, NETWORKING_AWAY, DATA_FORMAT, AUTHENTICATION_METHOD, SIGNATURE_CODE, DYNAMIC_REGISTRATION, STATUS, DELETED, DESCRIPTION, CREATE_TIME);
    }
}
