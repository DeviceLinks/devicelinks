package cn.devicelinks.jdbc.tables;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.entity.DataChart;
import cn.devicelinks.jdbc.ColumnValueMappers;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DataChart} Table implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TDataChart extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    public static final TDataChart DATA_CHART = new TDataChart("data_chart");

    private TDataChart(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column TARGET_LOCATION = Column.withName("target_location").typeMapper(ColumnValueMappers.DATA_CHART_TARGET_LOCATION).build();
    public final Column TARGET_ID = Column.withName("target_id").build();
    public final Column CHART_TYPE = Column.withName("chart_type").typeMapper(ColumnValueMappers.DATA_CHART_TYPE).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
    public final Column MARK = Column.withName("mark").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, TARGET_LOCATION, TARGET_ID, CHART_TYPE, DELETED, CREATE_BY, CREATE_TIME, MARK);
    }
}
