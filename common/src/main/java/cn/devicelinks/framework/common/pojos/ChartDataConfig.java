package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.ChartDataTargetLocation;
import cn.devicelinks.framework.common.ChartType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据图表配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class ChartDataConfig implements Serializable {
    private String id;
    private String name;
    private ChartDataTargetLocation targetLocation;
    private String targetId;
    private ChartType chartType;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
}
