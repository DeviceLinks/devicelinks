package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.SysUser;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysUser} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysUser extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TSysUser SYS_USER = new TSysUser("sys_user");

	private TSysUser(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column NAME = Column.withName("name").build();
	public final Column ACCOUNT = Column.withName("account").build();
	public final Column PWD = Column.withName("pwd").build();
	public final Column DEPARTMENT_ID = Column.withName("department_id").build();
	public final Column IDENTITY = Column.withName("identity").typeMapper(ColumnValueMappers.USER_IDENTITY).build();
	public final Column LAST_LOGIN_TIME = Column.withName("last_login_time").localDateTimeValue().build();
	public final Column LAST_CHANGE_PWD_TIME = Column.withName("last_change_pwd_time").localDateTimeValue().build();
	public final Column ENABLED = Column.withName("enabled").booleanValue().build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, ACCOUNT, PWD, DEPARTMENT_ID, IDENTITY, LAST_LOGIN_TIME, LAST_CHANGE_PWD_TIME, ENABLED, DELETED, CREATE_BY, CREATE_TIME);
    }
}