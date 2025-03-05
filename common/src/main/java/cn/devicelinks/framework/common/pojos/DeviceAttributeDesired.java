package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.DesiredAttributeStatus;
import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备期望属性
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceAttributeDesired implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String deviceId;
    private String moduleId;
    private String attributeId;
    private String identifier;
    private AttributeDataType dataType;
    private int version;
    private Object desiredValue;
    private DesiredAttributeStatus status;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime createTime;
    private LocalDateTime expiredTime;
}
