package cn.devicelinks.console.model;

import cn.devicelinks.framework.common.UserActivateMethod;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加用户请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddUserRequest {

    @NotEmpty(message = "用户名称不可以为空")
    @Length(max = 30, message = "用户名称最大允许传递30个字符串")
    private String username;

    @NotEmpty(message = "用户账号不可以为空")
    @Length(max = 30, message = "用户账号最大允许传递30个字符串")
    private String account;

    @NotNull(message = "用户激活方式不可以为空")
    @EnumValid(target = UserActivateMethod.class, message = "用户激活方式不允许传递非法值")
    private String activateMethod;

    @Email(message = "邮箱地址格式非法")
    private String email;

    private String phone;

    private String departmentId;

    private String mark;
}
