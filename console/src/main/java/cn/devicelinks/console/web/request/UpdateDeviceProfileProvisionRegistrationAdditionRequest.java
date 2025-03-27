package cn.devicelinks.console.web.request;

import cn.devicelinks.framework.common.ProvisionRegistrationStrategy;
import cn.devicelinks.framework.common.pojos.DeviceProfileProvisionRegistrationAddition;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新设备配置文件设备预注册附加配置请求
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceProfileProvisionRegistrationAdditionRequest {

    @EnumValid(target = ProvisionRegistrationStrategy.class, message = "预注册策略参数非法.")
    @NotBlank
    private String provisionRegistrationStrategy;

    private DeviceProfileProvisionRegistrationAddition provisionRegistrationAddition;
}
