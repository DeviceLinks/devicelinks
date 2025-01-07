package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.ProtocolGateway;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link ProtocolGateway} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TProtocolGateway extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TProtocolGateway PROTOCOL_GATEWAY = new TProtocolGateway("protocol_gateway");

    private TProtocolGateway(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column PROTOCOL = Column.withName("protocol").typeMapper(ColumnValueMappers.GATEWAY_PROTOCOL).build();
    public final Column PORT = Column.withName("port").intValue().build();
    public final Column URL = Column.withName("url").build();
    public final Column AUTHENTICATION_METHOD = Column.withName("authentication_method").typeMapper(ColumnValueMappers.GATEWAY_AUTHENTICATION_METHOD).build();
    public final Column AUTHENTICATION_URL = Column.withName("authentication_url").build();
    public final Column TRANSPORT_PROTOCOL = Column.withName("transport_protocol").typeMapper(ColumnValueMappers.DATA_TRANSPORT_PROTOCOL).build();
    public final Column SERVER_CA = Column.withName("server_ca").build();
    public final Column SERVER_CA_KEY = Column.withName("server_ca_key").build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.GATEWAY_STATUS).build();
    public final Column CONTAINER_ID = Column.withName("container_id").build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, PRODUCT_ID, PROTOCOL, PORT, URL, AUTHENTICATION_METHOD, AUTHENTICATION_URL, TRANSPORT_PROTOCOL, SERVER_CA, SERVER_CA_KEY, STATUS, CONTAINER_ID, DELETED, CREATE_BY, CREATE_TIME);
    }
}
