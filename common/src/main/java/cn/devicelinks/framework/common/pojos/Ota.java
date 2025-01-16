package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.OtaPackageType;
import cn.devicelinks.framework.common.SignAlgorithm;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * OTA升级信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Ota implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String productId;
    private String moduleId;
    private OtaPackageType type;
    private String version;
    private SignAlgorithm signAlgorithm;
    private boolean signWithKey;
    private boolean verify;
    private List<String> upgradeItem;
    private OtaAddition addition;
    private boolean enabled;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
    private String mark;
}
