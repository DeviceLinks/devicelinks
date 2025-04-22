package cn.devicelinks.api.model.dto;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.AttributeScope;
import cn.devicelinks.framework.common.AttributeValueSource;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备属性最新值数据传输实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DeviceAttributeLatestDTO {
    private String attributeId;
    private String attributeName;
    private String identifier;
    private AttributeDataType attributeDataType;
    private AttributeScope scope;
    private String unitName;
    private Object lastReportValue;
    private AttributeValueSource valueSource;
    private LocalDateTime lastReportTime;
    private Object lastDesiredValue;
    private LocalDateTime lastDesiredTime;
}
