package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.DeviceShadowStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备影子
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceShadow implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String deviceId;
    private List<DeviceShadowDataAddition> shadowData;
    private DeviceShadowStatus status;
    private LocalDateTime lastUpdateTimestamp;
    private LocalDateTime lastSyncTimestamp;
    private LocalDateTime createTime;
}
