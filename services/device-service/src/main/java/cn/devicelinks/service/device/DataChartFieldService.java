package cn.devicelinks.service.device;

import cn.devicelinks.framework.common.pojos.DataChartField;
import cn.devicelinks.framework.jdbc.BaseService;

import java.util.List;

/**
 * 数据图表字段业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DataChartFieldService extends BaseService<DataChartField, String> {
    /**
     * 获取数据图表的字段列表
     *
     * @param chartId 图表ID {@link DataChartField#getChartId()}
     * @return {@link DataChartField}
     */
    List<DataChartField> getFieldListByChartId(String chartId);
}
