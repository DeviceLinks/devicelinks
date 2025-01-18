package cn.devicelinks.framework.jdbc.model.dto;

import cn.devicelinks.framework.common.pojos.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户数据传输实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
    private String departmentName;
}
