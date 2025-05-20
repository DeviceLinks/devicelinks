package cn.devicelinks.api.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量更新多个用户的所属部门
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class BatchChangeUserDepartmentRequest {
    @NotEmpty(message = "请至少传递一个用户ID.")
    private List<String> userIds;
}
