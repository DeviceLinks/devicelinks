package cn.devicelinks.console.service;

import cn.devicelinks.console.web.request.AddDataChartRequest;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.common.pojos.ChartDataFields;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.model.dto.ChartDataDTO;

import java.util.List;

/**
 * 数据图表配置业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ChartDataConfigService extends BaseService<ChartDataConfig, String> {
    /**
     * 添加数据图表
     *
     * @param chartDataConfig 数据图表配置
     * @param fields          图表中的字段列表
     * @return 添加后的数据图表传输实体 {@link ChartDataDTO}
     */
    ChartDataDTO addChart(ChartDataConfig chartDataConfig, List<ChartDataFields> fields);

    /**
     * 添加数据图表
     *
     * @param request 添加数据图表请求实体 {@link AddDataChartRequest}
     * @return 添加后的数据图表传输实体 {@link ChartDataDTO}
     */
    ChartDataDTO addChart(AddDataChartRequest request);

    /**
     * 删除数据图表
     *
     * @param chartId 数据图表ID {@link ChartDataConfig#getId()}
     * @return 已删除的数据图表
     */
    ChartDataConfig deleteChart(String chartId);
}
