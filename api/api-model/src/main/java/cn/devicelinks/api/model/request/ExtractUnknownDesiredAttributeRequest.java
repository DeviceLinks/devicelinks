package cn.devicelinks.api.model.request;

import cn.devicelinks.framework.common.pojos.AttributeAddition;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 提取未知期望属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class ExtractUnknownDesiredAttributeRequest {

    @NotEmpty(message = "属性名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "属性名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(min = 1, max = 30, message = "属性名称长度必须在1 ~ 30个字符之间")
    private String attributeName;

    private AttributeAddition addition;

    @Length(max = 100, message = "属性描述不可以超过100个字符")
    private String description;
}
