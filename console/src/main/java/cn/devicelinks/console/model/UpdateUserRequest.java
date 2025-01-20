package cn.devicelinks.console.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新用户信息请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateUserRequest {

    @NotEmpty(message = "用户名称不可以为空")
    @Length(max = 30, message = "用户名称最大允许传递30个字符串")
    private String username;

    @Email(message = "邮箱地址格式非法")
    private String email;

    @NotEmpty
    private String departmentId;

    private String phone;

    private String mark;
}
