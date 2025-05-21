package cn.devicelinks.api.model.request;

import cn.devicelinks.common.UpdateDeviceEnabledAction;
import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量更新设备启用/禁用状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class BatchUpdateDeviceEnabledRequest {
    @NotBlank(message = "设备更新状态动作不可为空")
    @EnumValid(target = UpdateDeviceEnabledAction.class, message = "不支持的更新设备状态动作.")
    private String action;
    @NotEmpty(message = "请至少传递一个设备ID")
    private List<String> deviceIds;
}
