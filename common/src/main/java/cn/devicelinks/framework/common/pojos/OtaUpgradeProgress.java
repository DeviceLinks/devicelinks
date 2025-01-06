package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.OtaUpgradeProgressState;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * OTA升级进度
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class OtaUpgradeProgress implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String deviceId;
    private String otaId;
    private String otaBatchId;
    private int progress;
    private OtaUpgradeProgressState state;
    private String stateDesc;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;
    private String failureReason;
}
