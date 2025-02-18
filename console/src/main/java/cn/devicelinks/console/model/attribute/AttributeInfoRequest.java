package cn.devicelinks.console.model.attribute;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.pojos.AttributeAddition;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AttributeInfoRequest {

    @NotEmpty(message = "属性名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "属性名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(min = 1, max = 30, message = "属性名称长度必须在1 ~ 30个字符之间")
    private String name;

    @NotEmpty(message = "属性标识符不可以为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "属性标识符只允许包含字母、数字和下划线，且必须以字母开头")
    @Length(min = 1, max = 50, message = "属性标识符长度必须在1 ~ 50个字符之间")
    private String identifier;

    @NotEmpty(message = "属性数据类型参数值不可以为空")
    @EnumValid(target = AttributeDataType.class, message = "属性数据类型参数值非法")
    private String dataType;

    private AttributeAddition addition;

    @Length(max = 100, message = "属性描述不可以超过100个字符")
    private String description;
}
