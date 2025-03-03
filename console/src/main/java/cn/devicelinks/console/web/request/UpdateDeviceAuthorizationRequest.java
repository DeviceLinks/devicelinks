package cn.devicelinks.console.web.request;

import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 更新设备凭证请求实体类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceAuthorizationRequest {

    @NotEmpty(message = "设备鉴权方式不可以为空")
    @EnumValid(target = DeviceAuthenticationMethod.class, message = "设备鉴权方式参数非法")
    private String authenticationMethod;

    private DeviceAuthenticationAddition authenticationAddition;
}
