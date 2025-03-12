package cn.devicelinks.framework.jdbc.model.dto;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.pojos.AttributeAddition;
import cn.devicelinks.framework.common.pojos.DeviceAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备属性数据转换类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceAttributeDTO extends DeviceAttribute {

    private String attributeName;

    private AttributeDataType attributeDataType;

    private AttributeAddition attributeAddition;

    private String attributeDescription;
}
