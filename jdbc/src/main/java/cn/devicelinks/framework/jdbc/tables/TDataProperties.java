package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link cn.devicelinks.framework.common.pojos.DataProperties} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDataProperties extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDataProperties DATA_PROPERTIES = new TDataProperties("data_properties");

    private TDataProperties(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column PID = Column.withName("pid").build();
    public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.DATA_PROPERTIES_TYPE).build();
    public final Column NAME = Column.withName("name").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column DATA_TYPE = Column.withName("data_type").typeMapper(ColumnValueMappers.DATA_PROPERTIES_DATA_TYPE).build();
    public final Column RW_TYPE = Column.withName("rw_type").typeMapper(ColumnValueMappers.DATA_PROPERTIES_RW_TYPE).build();
    public final Column DESCRIPTION = Column.withName("description").build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.JSON_MAP).build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, MODULE_ID, PID, TYPE, NAME, IDENTIFIER, DATA_TYPE, RW_TYPE, DESCRIPTION, ADDITION, ENABLED, DELETED, CREATE_BY, CREATE_TIME);
    }
}
