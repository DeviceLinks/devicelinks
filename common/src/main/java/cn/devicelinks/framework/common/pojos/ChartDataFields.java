package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.ChartDataFieldType;
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
public class ChartDataFields implements Serializable {
    private String id;
    private String configId;
    private ChartDataFieldType fieldType;
    private String fieldId;
    private String fieldIdentifier;
    private String fieldLabel;
    private LocalDateTime createTime;
}
