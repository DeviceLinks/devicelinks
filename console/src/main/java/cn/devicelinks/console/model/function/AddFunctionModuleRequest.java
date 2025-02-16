package cn.devicelinks.console.model.function;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "功能模块名称不可以为空")
    @Length(max = 32, message = "功能模块名称不可以超过32个字符")
    private String productId;

    @NotEmpty(message = "功能模块名称不可以为空")
    @Length(max = 30, message = "功能模块名称不可以超过30个字符")
    private String name;

    @NotEmpty(message = "功能模块标识符不可以为空")
    @Length(max = 30, message = "功能模块标识符不可以超过30个字符")
    private String identifier;
}
