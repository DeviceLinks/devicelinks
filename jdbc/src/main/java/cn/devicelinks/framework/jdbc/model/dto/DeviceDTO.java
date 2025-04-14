package cn.devicelinks.framework.jdbc.model.dto;

import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.pojos.Device;
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
