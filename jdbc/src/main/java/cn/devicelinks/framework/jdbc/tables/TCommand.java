package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link cn.devicelinks.framework.common.pojos.Command} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TCommand extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TCommand COMMAND = new TCommand("command");

    private TCommand(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEVICE_ID = Column.withName("device_id").build();
    public final Column CONTENT = Column.withName("content").build();
    public final Column PARAMS = Column.withName("params").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.COMMAND_STATUS).build();
    public final Column SEND_TIME = Column.withName("send_time").localDateTimeValue().build();
    public final Column RESPONSE_TIME = Column.withName("response_time").localDateTimeValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
    public final Column FAILURE_REASON = Column.withName("failure_reason").build();


    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEVICE_ID, CONTENT, PARAMS, STATUS, SEND_TIME, RESPONSE_TIME, CREATE_BY, CREATE_TIME, FAILURE_REASON);
    }
}
