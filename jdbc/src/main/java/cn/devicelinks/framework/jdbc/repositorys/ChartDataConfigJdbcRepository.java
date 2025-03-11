package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TChartDataConfig.CHART_DATA_CONFIG;

/**
 * The {@link ChartDataConfig} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ChartDataConfigJdbcRepository extends JdbcRepository<ChartDataConfig, String> implements ChartDataConfigRepository {
	public ChartDataConfigJdbcRepository(JdbcOperations jdbcOperations) {
		super(CHART_DATA_CONFIG, jdbcOperations);
	}
}
