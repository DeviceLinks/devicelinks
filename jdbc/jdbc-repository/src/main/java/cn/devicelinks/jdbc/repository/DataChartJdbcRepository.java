package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DataChart;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.sql.Condition;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TDataChart.DATA_CHART;

/**
 * The {@link DataChart} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DataChartJdbcRepository extends JdbcRepository<DataChart, String> implements DataChartRepository {
    public DataChartJdbcRepository(JdbcOperations jdbcOperations) {
        super(DATA_CHART, jdbcOperations);
    }

    @Override
    public List<DataChart> getDataChartList(List<SearchFieldCondition> searchFieldConditionList) {
        Condition[] conditions = searchFieldConditionToConditionArray(searchFieldConditionList);
        List<Condition> conditionList = new ArrayList<>(List.of(conditions));
        conditionList.add(DATA_CHART.DELETED.eq(Boolean.FALSE));
        return this.select(conditionList.toArray(Condition[]::new));
    }
}
