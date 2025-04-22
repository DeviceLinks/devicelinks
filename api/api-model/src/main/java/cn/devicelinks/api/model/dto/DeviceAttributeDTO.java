package cn.devicelinks.api.model.dto;

import cn.devicelinks.common.AttributeDataType;
import cn.devicelinks.entity.AttributeAddition;
import cn.devicelinks.entity.DeviceAttribute;
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
