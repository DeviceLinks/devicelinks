package cn.devicelinks.api.model.dto;

import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.entity.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 设备基本信息数据传输实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceDTO extends Device {

    private DeviceCredentialsType credentialsType;

    private Map<String, String> moduleVersion;
}
