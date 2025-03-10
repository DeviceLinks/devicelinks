package cn.devicelinks.console.web.request;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.pojos.AttributeAddition;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 提取未知属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class ExtractUnknownLatestAttributeRequest {

    @NotEmpty(message = "属性名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "属性名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(min = 1, max = 30, message = "属性名称长度必须在1 ~ 30个字符之间")
    private String attributeName;

    private AttributeAddition addition;

    @NotEmpty(message = "属性数据类型参数值不可以为空")
    @EnumValid(target = AttributeDataType.class, message = "属性数据类型参数值非法")
    private String dataType;

    private boolean writable;

    @Length(max = 100, message = "属性描述不可以超过100个字符")
    private String description;
}
