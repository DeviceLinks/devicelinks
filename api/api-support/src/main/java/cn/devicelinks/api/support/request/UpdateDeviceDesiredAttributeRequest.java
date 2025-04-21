package cn.devicelinks.api.support.request;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新期望属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceDesiredAttributeRequest {

    @EnumValid(target = AttributeDataType.class, message = "属性数据类型参数值非法")
    private String dataType;

    @NotBlank(message = "期望属性值不可以为空")
    private String desiredValue;
}
