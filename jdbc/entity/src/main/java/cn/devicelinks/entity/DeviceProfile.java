package cn.devicelinks.entity;

import cn.devicelinks.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 设备配置文件
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceProfile implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String name;
    private boolean defaultProfile;
    private String productId;
    private String firmwareId;
    private String softwareId;
    private DeviceProfileLogAddition logAddition;
    private DeviceProfileAlarmAddition alarmAddition;
    private DeviceProfileProvisionAddition provisionAddition;
    private Map<String, Object> extension;
    private String createBy;
    private LocalDateTime createTime;
    private boolean deleted;
    private String description;
}
