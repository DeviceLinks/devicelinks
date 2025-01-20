package cn.devicelinks.console.model;

import cn.devicelinks.framework.common.UserIdentity;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 获取用户列表请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UsersQuery {

    @Length(max = 50)
    private String name;

    @Length(max = 32)
    private String departmentId;

    @EnumValid(target = UserIdentity.class, message = "用户身份不允许传递非法值")
    private String identity;
}
