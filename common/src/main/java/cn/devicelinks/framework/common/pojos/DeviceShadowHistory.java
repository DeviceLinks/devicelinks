package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.DeviceShadowHistoryOperationSource;
import cn.devicelinks.framework.common.DeviceShadowHistoryOperationType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 设备影子历史记录
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceShadowHistory implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String deviceId;
    private String shadowName;
    private DeviceShadowHistoryOperationType operationType;
    private long previousVersion;
    private long currentVersion;
    private Map<String, Object> shadowData;
    private Map<String, Object> delta;
    private Long operationTimestamp;
    private DeviceShadowHistoryOperationSource operationSource;
    private LocalDateTime createTime;
}
