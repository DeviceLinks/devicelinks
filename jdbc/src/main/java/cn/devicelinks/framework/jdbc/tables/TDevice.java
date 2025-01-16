package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.Device;

import java.io.Serial;
import java.util.List;

/**
 * The {@link Device} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDevice extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TDevice DEVICE = new TDevice("device");

	private TDevice(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEPARTMENT_ID = Column.withName("department_id").build();
	public final Column PRODUCT_ID = Column.withName("product_id").build();
	public final Column DEVICE_CODE = Column.withName("device_code").build();
	public final Column DEVICE_SECRET = Column.withName("device_secret").build();
	public final Column DEVICE_TYPE = Column.withName("device_type").typeMapper(ColumnValueMappers.DEVICE_TYPE).build();
	public final Column NAME = Column.withName("name").build();
	public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.DEVICE_STATUS).build();
	public final Column TAGS = Column.withName("tags").typeMapper(ColumnValueMappers.STRING_JOINER).build();
	public final Column ACTIVATION_TIME = Column.withName("activation_time").localDateTimeValue().build();
	public final Column LAST_ONLINE_TIME = Column.withName("last_online_time").localDateTimeValue().build();
	public final Column LAST_REPORT_TIME = Column.withName("last_report_time").localDateTimeValue().build();
	public final Column ENABLED = Column.withName("enabled").booleanValue().build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.DEVICE_ADDITION).build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
	public final Column MARK = Column.withName("mark").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEPARTMENT_ID, PRODUCT_ID, DEVICE_CODE, DEVICE_SECRET, DEVICE_TYPE, NAME, STATUS, TAGS, ACTIVATION_TIME, LAST_ONLINE_TIME, LAST_REPORT_TIME, ENABLED, DELETED, ADDITION, CREATE_BY, CREATE_TIME, MARK);
    }
}