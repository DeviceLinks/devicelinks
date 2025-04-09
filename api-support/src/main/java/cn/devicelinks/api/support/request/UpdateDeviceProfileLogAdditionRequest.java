package cn.devicelinks.api.support.request;

import cn.devicelinks.framework.common.pojos.DeviceProfileLogAddition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新设备配置文件日志附加配置请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceProfileLogAdditionRequest {
    @NotNull
    DeviceProfileLogAddition logAddition;
}
