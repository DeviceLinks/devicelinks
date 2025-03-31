package cn.devicelinks.framework.common.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备配置文件 - 预配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceProfileProvisionAddition {
    private String provisionDeviceKey;
    private String provisionDeviceSecret;
    private long validSeconds;
}
