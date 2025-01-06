package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysPosition;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysPosition} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysPosition extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysPosition SYS_POSITION = new TSysPosition("sys_position");

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column MARK = Column.withName("mark").build();

    private TSysPosition(String tableName) {
        super(tableName);
    }

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, DELETED, ENABLED, CREATE_TIME, CREATE_BY, MARK);
    }
}
