package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.NotificationRule;

import java.io.Serial;
import java.util.List;

/**
 * The {@link NotificationRule} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TNotificationRule extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TNotificationRule NOTIFICATION_RULE = new TNotificationRule("notification_rule");

	private TNotificationRule(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column TYPE_ID = Column.withName("type_id").build();
	public final Column TEMPLATE_ID = Column.withName("template_id").build();
	public final Column RECEIVER_ID = Column.withName("receiver_id").build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.NOTIFICATION_RULE_ADDITION).build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, TYPE_ID, TEMPLATE_ID, RECEIVER_ID, ADDITION, CREATE_BY, CREATE_TIME);
    }
}