package cn.devicelinks.console.service;

import cn.devicelinks.console.web.request.AddDataChartRequest;
import cn.devicelinks.framework.common.pojos.DataChart;
import cn.devicelinks.framework.common.pojos.DataChartField;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.model.dto.DataChartDTO;

import java.util.List;

/**
 * 数据图表业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DataChartService extends BaseService<DataChart, String> {
    /**
     * 分页查询数据图表
     *
     * @param searchFieldConditionList 检索字段查询条件列表
     * @return 数据图表传输实体
     */
    List<DataChartDTO> getDataChartList(List<SearchFieldCondition> searchFieldConditionList);

    /**
     * 添加数据图表
     *
     * @param dataChart 数据图表
     * @param fields    图表中的字段列表
     * @return 添加后的数据图表传输实体 {@link DataChartDTO}
     */
    DataChartDTO addChart(DataChart dataChart, List<DataChartField> fields);

    /**
     * 添加数据图表
     *
     * @param request 添加数据图表请求实体 {@link AddDataChartRequest}
     * @return 添加后的数据图表传输实体 {@link DataChartDTO}
     */
    DataChartDTO addChart(AddDataChartRequest request);

    /**
     * 删除数据图表
     *
     * @param chartId 数据图表ID {@link DataChart#getId()}
     * @return 已删除的数据图表
     */
    DataChart deleteChart(String chartId);
}
