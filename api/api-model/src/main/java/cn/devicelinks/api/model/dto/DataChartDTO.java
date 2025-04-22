package cn.devicelinks.api.model.dto;

import cn.devicelinks.entity.DataChart;
import cn.devicelinks.entity.DataChartField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 数据图表传输实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataChartDTO extends DataChart {
    private List<DataChartField> fields;
}
