package cn.devicelinks.api.model.request;

import cn.devicelinks.common.DeviceProfileBatchSetAway;
import cn.devicelinks.common.DeviceType;
import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 批量设置设备配置文件请求实体参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class BatchSetDeviceProfileRequest {

    @EnumValid(target = DeviceProfileBatchSetAway.class, message = "批量设置方式参数值非法.")
    @NotBlank
    private String batchSetAway;

    private List<String> deviceIds;

    private List<String> deviceTags;

    @EnumValid(target = DeviceType.class, message = "设备类型参数值非法.")
    private String deviceType;
}
