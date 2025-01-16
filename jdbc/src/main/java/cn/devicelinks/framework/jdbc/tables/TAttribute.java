package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link Attribute} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TAttribute extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TAttribute ATTRIBUTE = new TAttribute("attribute");

    private TAttribute(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column MODULE_ID = Column.withName("module_id").build();
    public final Column PID = Column.withName("pid").build();
    public final Column NAME = Column.withName("name").build();
    public final Column IDENTIFIER = Column.withName("identifier").build();
    public final Column DATA_TYPE = Column.withName("data_type").typeMapper(ColumnValueMappers.ATTRIBUTE_DATA_TYPE).build();
    public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.ATTRIBUTE_ADDITION).build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
    public final Column DESCRIPTION = Column.withName("description").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, PRODUCT_ID, MODULE_ID, PID, NAME, IDENTIFIER, DATA_TYPE, ADDITION, ENABLED, DELETED, CREATE_BY, CREATE_TIME, DESCRIPTION);
    }
}
