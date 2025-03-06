package cn.devicelinks.console.web.request;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加设备期望属性请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDeviceDesiredAttributeRequest {

    @NotEmpty(message = "设备ID不可以为空")
    @Length(max = 24, message = "设备ID不可以超过24个字符")
    private String deviceId;

    @NotEmpty(message = "功能模块ID不可以为空")
    @Length(max = 24, message = "功能模块ID不可以超过24个字符")
    private String moduleId;

    @Length(max = 24, message = "属性ID不可以超过24个字符")
    private String attributeId;

    @NotEmpty(message = "属性标识符不可以为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "属性标识符只允许包含字母、数字和下划线，且必须以字母开头")
    @Length(min = 1, max = 50, message = "属性标识符长度必须在1 ~ 50个字符之间")
    private String identifier;

    @NotEmpty(message = "属性数据类型参数值不可以为空")
    @EnumValid(target = AttributeDataType.class, message = "属性数据类型参数值非法")
    private String dataType;

    private String desiredValue;
}
