package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysDepartmentLevel;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysDepartmentLevel} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysDepartmentLevel extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysDepartmentLevel SYS_DEPARTMENT_LEVEL = new TSysDepartmentLevel("sys_department_level");

    private TSysDepartmentLevel(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column DEPARTMENT_ID = Column.withName("department_id").build();
    public final Column PARENT_DEPARTMENT_ID = Column.withName("parent_department_id").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, DEPARTMENT_ID, PARENT_DEPARTMENT_ID, CREATE_TIME);
    }
}
