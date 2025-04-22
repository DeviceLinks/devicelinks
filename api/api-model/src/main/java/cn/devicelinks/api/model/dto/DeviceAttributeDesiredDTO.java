package cn.devicelinks.api.model.dto;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.pojos.AttributeAddition;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备期望属性数据转换类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceAttributeDesiredDTO extends DeviceAttributeDesired {

    private String attributeName;

    private AttributeDataType attributeDataType;

    private AttributeAddition attributeAddition;

    private String attributeDescription;
}
