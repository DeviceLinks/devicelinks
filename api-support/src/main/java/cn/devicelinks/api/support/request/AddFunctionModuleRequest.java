package cn.devicelinks.api.support.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 新增功能模块请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddFunctionModuleRequest {
    @NotEmpty(message = "产品ID不可以为空")
    @Length(max = 32, message = "产品ID不可以超过32个字符")
    private String productId;

    @NotEmpty(message = "功能模块名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "功能模块名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(max = 30, message = "功能模块名称长度最大支持30个字符")
    private String name;

    @NotEmpty(message = "功能模块标识符不可以为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "功能模块标识符只允许包含字母、数字和下划线，且必须以字母开头")
    @Length(max = 50, message = "功能模块标识符长度最大支持50个字符")
    private String identifier;
}
