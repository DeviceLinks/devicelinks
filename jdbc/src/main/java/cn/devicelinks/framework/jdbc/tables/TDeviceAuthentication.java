package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;

import java.io.Serial;
import java.util.List;

/**
 * The {@link DeviceAuthentication} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDeviceAuthentication extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TDeviceAuthentication DEVICE_AUTHENTICATION = new TDeviceAuthentication("device_authentication");

	private TDeviceAuthentication(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEVICE_ID = Column.withName("device_id").build();
	public final Column AUTHENTICATION_METHOD = Column.withName("authentication_method").typeMapper(ColumnValueMappers.DEVICE_AUTHENTICATION_METHOD).build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.DEVICE_AUTHENTICATION_ADDITION).build();
	public final Column EXPIRATION_TIME = Column.withName("expiration_time").localDateTimeValue().build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, AUTHENTICATION_METHOD, ADDITION, EXPIRATION_TIME, DELETED, CREATE_TIME);
    }
}