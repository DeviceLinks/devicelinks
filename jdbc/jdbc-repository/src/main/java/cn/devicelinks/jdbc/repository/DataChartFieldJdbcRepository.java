package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DataChartField;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.jdbc.tables.TDataChartField.DATA_CHART_FIELD;

/**
 * The {@link DataChartField} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DataChartFieldJdbcRepository extends JdbcRepository<DataChartField, String> implements DataChartFieldRepository {
	public DataChartFieldJdbcRepository(JdbcOperations jdbcOperations) {
		super(DATA_CHART_FIELD, jdbcOperations);
	}
}
