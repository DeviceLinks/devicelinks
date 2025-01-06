package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysDepartment} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysDepartment extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysDepartment SYS_DEPARTMENT = new TSysDepartment("sys_department");

    private TSysDepartment(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column CODE = Column.withName("code").build();
    public final Column PID = Column.withName("pid").build();
    public final Column SORT = Column.withName("sort").intValue().build();
    public final Column LEVEL = Column.withName("level").intValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column MARK = Column.withName("mark").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, CODE, PID, SORT, LEVEL, CREATE_BY, CREATE_TIME, DELETED, MARK);
    }
}
