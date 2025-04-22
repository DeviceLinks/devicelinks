package cn.devicelinks.entity;

import cn.devicelinks.common.DataChartTargetLocation;
import cn.devicelinks.common.DataChartType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据图表
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DataChart implements Serializable {
    private String id;
    private String name;
    private DataChartTargetLocation targetLocation;
    private String targetId;
    private DataChartType chartType;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
    private String mark;
}
