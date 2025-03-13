package cn.devicelinks.framework.jdbc.model.dto;

import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.common.pojos.ChartDataFields;
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
public class ChartDataDTO extends ChartDataConfig {
    private List<ChartDataFields> fields;
}
