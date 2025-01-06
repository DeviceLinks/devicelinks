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
import java.util.Map;

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
    private String versionBefore;
    private String version;
    private SignAlgorithm signAlgorithm;
    private boolean signWithKey;
    private boolean verify;
    private List<String> upgradeItem;
    private Map<String, Object> addition;
    private boolean enabled;
    private boolean deleted;
    private LocalDateTime createTime;
    private String createBy;
    private String mark;
}
