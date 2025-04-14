package cn.devicelinks.api.support.request;

import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.pojos.DeviceCredentialsAddition;
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
public class UpdateDeviceCredentialsRequest {

    @NotEmpty(message = "设备鉴权方式不可以为空")
    @EnumValid(target = DeviceCredentialsType.class, message = "设备鉴权方式参数非法")
    private String credentialsType;

    private DeviceCredentialsAddition authenticationAddition;
}
