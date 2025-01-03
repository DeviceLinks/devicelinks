package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysGroup;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysGroup} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysGroup extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysGroup SYS_GROUP = new TSysGroup("sys_group");

    private TSysGroup(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column TENANT_ID = Column.withName("tenant_id").build();
    public final Column DEPARTMENT_ID = Column.withName("department_id").build();
    public final Column NAME = Column.withName("name").build();
    public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.GROUP_TYPE).build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, TENANT_ID, DEPARTMENT_ID, TYPE, ENABLED, DELETED, CREATE_BY, CREATE_TIME);
    }
}
