package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.NotificationType;

import java.io.Serial;
import java.util.List;

/**
 * The {@link NotificationType} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TNotificationType extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TNotificationType NOTIFICATION_TYPE = new TNotificationType("notification_type");

	private TNotificationType(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column IDENTIFIER = Column.withName("identifier").typeMapper(ColumnValueMappers.NOTIFICATION_TYPE_IDENTIFIER).build();
	public final Column ENABLED = Column.withName("enabled").booleanValue().build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, IDENTIFIER, ENABLED, CREATE_TIME);
    }
}