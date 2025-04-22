package cn.devicelinks.api.model.request;

import cn.devicelinks.entity.DeviceProfileProvisionAddition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 设备配置文件 - 更新预配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceProfileProvisionAdditionRequest {
    @NotNull
    private DeviceProfileProvisionAddition provisionAddition;
}
