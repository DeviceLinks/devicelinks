package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.ChartDataFields;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TChartDataFields.CHART_DATA_FIELDS;

/**
 * The {@link ChartDataFields} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ChartDataFieldsJdbcRepository extends JdbcRepository<ChartDataFields, String> implements ChartDataFieldsRepository {
	public ChartDataFieldsJdbcRepository(JdbcOperations jdbcOperations) {
		super(CHART_DATA_FIELDS, jdbcOperations);
	}
}
