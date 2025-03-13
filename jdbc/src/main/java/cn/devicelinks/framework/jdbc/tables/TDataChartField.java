package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.DataChartField;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DataChartField} Table implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDataChartField extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TDataChartField DATA_CHART_FIELD = new TDataChartField("data_chart_field");

    private TDataChartField(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column CHART_ID = Column.withName("chart_id").build();
    public final Column FIELD_TYPE = Column.withName("field_type").typeMapper(ColumnValueMappers.DATA_CHART_FIELD_TYPE).build();
    public final Column FIELD_ID = Column.withName("field_id").build();
    public final Column FIELD_IDENTIFIER = Column.withName("field_identifier").build();
    public final Column FIELD_LABEL = Column.withName("field_label").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, CHART_ID, FIELD_TYPE, FIELD_ID, FIELD_IDENTIFIER, FIELD_LABEL, CREATE_TIME);
    }
}
