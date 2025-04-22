package cn.devicelinks.jdbc.repository;

import cn.devicelinks.api.model.dto.DataChartDTO;
import cn.devicelinks.entity.DataChart;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;

import java.util.List;

/**
 * The {@link DataChart} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DataChartRepository extends Repository<DataChart, String> {
    /**
     * 分页查询图表
     *
     * @param searchFieldConditionList 检索字段条件列表
     * @return {@link DataChartDTO}
     */
    List<DataChart> getDataChartList(List<SearchFieldCondition> searchFieldConditionList);
}
