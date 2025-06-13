package cn.devicelinks.api.model.request;

import cn.devicelinks.common.AttributeDataType;
import cn.devicelinks.common.AttributeKnowType;
import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加设备属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDeviceAttributeRequest {

    @NotBlank(message = "属性定义类型不可以为空")
    @EnumValid(target = AttributeKnowType.class, message = "属性定义类型参数值非法")
    private String knowType;

    @NotBlank(message = "属性值不可以为空")
    private String value;

    @Length(max = 24, message = "属性ID不可以超过24个字符")
    private String attributeId;

    @NotBlank(message = "功能模块ID不可以为空")
    @Length(max = 24, message = "功能模块ID不可以超过24个字符")
    private String moduleId;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "属性标识符只允许包含字母、数字和下划线，且必须以字母开头")
    @Length(min = 1, max = 50, message = "属性标识符长度必须在1 ~ 50个字符之间")
    private String identifier;

    @EnumValid(target = AttributeDataType.class, message = "属性数据类型参数值非法")
    private String dataType;
}
