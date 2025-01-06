package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link cn.devicelinks.framework.common.pojos.DataPropertiesUnit} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDataPropertiesUnit extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDataPropertiesUnit DATA_PROPERTIES_UNIT = new TDataPropertiesUnit("data_properties_unit");

    private TDataPropertiesUnit(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column SYMBOL = Column.withName("symbol").build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().build();
    public final Column DELETED = Column.withName("deleted").booleanValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, SYMBOL, ENABLED, DELETED, CREATE_TIME);
    }
}
