package cn.devicelinks.console.model.attribute;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.pojos.AttributeAddition;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 添加属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AttributeInfoRequest {
    @NotEmpty(message = "属性名称不可以为空")
    private String name;

    @NotEmpty(message = "属性标识符不可以为空")
    private String identifier;

    @NotEmpty(message = "属性数据类型参数值不可以为空")
    @EnumValid(target = AttributeDataType.class, message = "属性数据类型参数值非法")
    private String dataType;

    private AttributeAddition addition;

    private String description;
}
