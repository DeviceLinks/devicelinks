package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysGroupUser;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysGroupUser} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysGroupUser extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysGroupUser SYS_GROUP_USER = new TSysGroupUser("sys_group_user");

    private TSysGroupUser(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column GROUP_ID = Column.withName("group_id").build();
    public final Column USER_ID = Column.withName("user_id").build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, GROUP_ID, USER_ID, CREATE_BY, CREATE_TIME);
    }
}
