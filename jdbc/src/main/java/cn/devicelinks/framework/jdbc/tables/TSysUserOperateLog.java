package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysUserOperateLog;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysUserOperateLog} Table Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysUserOperateLog extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysUserOperateLog SYS_USER_OPERATE_LOG = new TSysUserOperateLog("sys_user_operate_log");

    private TSysUserOperateLog(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    private final Column TENANT_ID = Column.withName("tenant_id").build();
    public final Column USER_ID = Column.withName("user_id").build();
    public final Column REQUEST_ID = Column.withName("request_id").build();
    public final Column RESOURCE_CODE = Column.withName("resource_code").build();
    public final Column ACTION = Column.withName("action").typeMapper(ColumnValueMappers.OPERATE_ACTION).build();
    public final Column OBJECT_TYPE = Column.withName("object_type").typeMapper(ColumnValueMappers.OPERATE_OBJECT_TYPE).build();
    public final Column OBJECT = Column.withName("object").build();
    public final Column OBJECT_FIELDS = Column.withName("object_fields").build();
    public final Column MSG = Column.withName("msg").build();
    public final Column SUCCESS = Column.withName("success").booleanValue().build();
    public final Column FAILURE_REASON = Column.withName("failure_reason").build();
    public final Column IP_ADDRESS = Column.withName("ip_address").build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column OPERATE_TIME = Column.withName("operate_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, TENANT_ID, USER_ID, REQUEST_ID, RESOURCE_CODE, ACTION, OBJECT_TYPE, OBJECT, MSG, OBJECT_FIELDS, SUCCESS, FAILURE_REASON, IP_ADDRESS, ADDITION, OPERATE_TIME);
    }
}
