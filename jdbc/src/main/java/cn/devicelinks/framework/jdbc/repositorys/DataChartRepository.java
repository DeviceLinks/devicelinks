package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.DataChart;
import cn.devicelinks.framework.jdbc.core.Repository;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.model.dto.DataChartDTO;

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
