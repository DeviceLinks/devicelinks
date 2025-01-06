package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.SysFile;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link SysFile} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysFile extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysFile SYS_FILE = new TSysFile("sys_file");

    private TSysFile(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column PATH = Column.withName("path").build();
    public final Column SIZE = Column.withName("size").intValue().build();
    public final Column SIGN_ALGORITHM = Column.withName("sign_algorithm").typeMapper(ColumnValueMappers.SIGN_ALGORITHM).build();
    public final Column SIGN_WITH_KEY = Column.withName("sign_with_key").booleanValue().build();
    public final Column SIGN = Column.withName("sign").build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, PATH, SIZE, SIGN_ALGORITHM, SIGN_WITH_KEY, SIGN, DELETED, CREATE_BY, CREATE_TIME);
    }
}
