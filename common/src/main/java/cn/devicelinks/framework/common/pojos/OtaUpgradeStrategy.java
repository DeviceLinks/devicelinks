package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.OtaPackageDownloadProtocol;
import cn.devicelinks.framework.common.OtaUpgradeStrategyRetryInterval;
import cn.devicelinks.framework.common.OtaUpgradeStrategyType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * OTA升级策略
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class OtaUpgradeStrategy implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String otaBatchId;
    private OtaUpgradeStrategyType type;
    private boolean activePush;
    private boolean confirmUpgrade;
    private OtaUpgradeStrategyRetryInterval retryInterval;
    private OtaPackageDownloadProtocol downloadProtocol;
    private boolean multipleModuleUpgrade;
    private boolean coverBeforeUpgrade;
    private Map<String, Object> tags;
    private String createBy;
    private LocalDateTime createTime;
}
