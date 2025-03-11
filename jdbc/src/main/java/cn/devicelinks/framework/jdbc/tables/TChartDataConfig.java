package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link ChartDataConfig} Table implementation
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TChartDataConfig extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    public static final TChartDataConfig CHART_DATA_CONFIG = new TChartDataConfig("chart_data_config");

    private TChartDataConfig(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column TARGET_LOCATION = Column.withName("target_location").typeMapper(ColumnValueMappers.CHART_DATA_TARGET_LOCATION).build();
    public final Column TARGET_ID = Column.withName("target_id").build();
    public final Column CHART_TYPE = Column.withName("chart_type").typeMapper(ColumnValueMappers.CHART_TYPE).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, TARGET_LOCATION, TARGET_ID, CHART_TYPE, CREATE_BY, CREATE_TIME);
    }
}
