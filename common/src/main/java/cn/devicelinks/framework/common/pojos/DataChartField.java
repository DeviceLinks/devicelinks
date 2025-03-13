package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DataChartFieldType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据图表字段
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DataChartField implements Serializable {
    private String id;
    private String chartId;
    private DataChartFieldType fieldType;
    private String fieldId;
    private String fieldIdentifier;
    private String fieldLabel;
    private LocalDateTime createTime;
}
