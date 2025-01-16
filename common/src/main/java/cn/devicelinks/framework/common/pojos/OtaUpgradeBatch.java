package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * OTA升级批次
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class OtaUpgradeBatch implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String otaId;
    private String name;
    private OtaUpgradeBatchType type;
    private OtaUpgradeBatchState state;
    private OtaUpgradeBatchMethod upgradeMethod;
    private OtaUpgradeBatchScope upgradeScope;
    private OtaUpgradeBatchAddition addition;
    private String createBy;
    private LocalDateTime createTime;
    private String mark;
}
