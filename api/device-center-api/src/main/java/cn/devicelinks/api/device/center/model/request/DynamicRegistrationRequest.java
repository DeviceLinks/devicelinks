package cn.devicelinks.api.device.center.model.request;

import cn.devicelinks.common.DynamicRegistrationMethod;
import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备动态注册请求对象实例
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DynamicRegistrationRequest {
    @EnumValid(target = DynamicRegistrationMethod.class, message = "设备注册方式不允许传递非法值")
    private String registrationMethod;
    private String profileId;
    private String productId;
    @NotBlank(message = "设备名称不可以为空")
    private String deviceName;
}
