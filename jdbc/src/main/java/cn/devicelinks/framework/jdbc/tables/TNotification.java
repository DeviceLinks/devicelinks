package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.Notification;

import java.io.Serial;
import java.util.List;

/**
 * The {@link Notification} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TNotification extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TNotification NOTIFICATION = new TNotification("notification");

	private TNotification(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column DEPARTMENT_ID = Column.withName("department_id").build();
	public final Column TYPE_ID = Column.withName("type_id").build();
	public final Column SUBJECT = Column.withName("subject").build();
	public final Column MESSAGE = Column.withName("message").build();
	public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.NOTIFICATION_STATUS).build();
	public final Column SEVERITY = Column.withName("severity").typeMapper(ColumnValueMappers.NOTIFICATION_SEVERITY).build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.NOTIFICATION_ADDITION).build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, DEPARTMENT_ID, TYPE_ID, SUBJECT, MESSAGE, STATUS, SEVERITY, ADDITION, CREATE_TIME);
    }
}