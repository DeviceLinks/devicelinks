package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DataChart;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDataChart.DATA_CHART;

/**
 * The {@link DataChart} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DataChartJdbcRepository extends JdbcRepository<DataChart, String> implements DataChartRepository {
	public DataChartJdbcRepository(JdbcOperations jdbcOperations) {
		super(DATA_CHART, jdbcOperations);
	}
}
