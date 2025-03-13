package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DataChartField;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDataChartField.DATA_CHART_FIELD;

/**
 * The {@link DataChartField} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DataChartFieldJdbcRepository extends JdbcRepository<DataChartField, String> implements DataChartFieldRepository {
	public DataChartFieldJdbcRepository(JdbcOperations jdbcOperations) {
		super(DATA_CHART_FIELD, jdbcOperations);
	}
}
