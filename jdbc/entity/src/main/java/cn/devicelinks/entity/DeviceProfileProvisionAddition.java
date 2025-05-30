package cn.devicelinks.entity;

import cn.devicelinks.common.DeviceProvisionStrategy;
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
    /**
     * 预配置策略
     */
    private DeviceProvisionStrategy strategy;
    /**
     * 预配置Key
     */
    private String provisionDeviceKey;
    /**
     * 预配置明文Secret
     */
    private String provisionDeviceSecret;
}
