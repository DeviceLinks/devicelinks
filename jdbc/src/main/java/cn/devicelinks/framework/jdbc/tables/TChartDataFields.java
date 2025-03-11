package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.ChartDataFields;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link ChartDataFields} Table implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TChartDataFields extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TChartDataFields CHART_DATA_FIELDS = new TChartDataFields("chart_data_fields");

    private TChartDataFields(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column CONFIG_ID = Column.withName("config_id").build();
    public final Column FIELD_TYPE = Column.withName("field_type").typeMapper(ColumnValueMappers.CHART_DATA_FIELD_TYPE).build();
    public final Column FIELD_ID = Column.withName("field_id").build();
    public final Column FIELD_IDENTIFIER = Column.withName("field_identifier").build();
    public final Column FIELD_LABEL = Column.withName("field_label").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, CONFIG_ID, FIELD_TYPE, FIELD_ID, FIELD_IDENTIFIER, FIELD_LABEL, CREATE_TIME);
    }
}
