package cn.devicelinks.entity;

import cn.devicelinks.common.AttributeDataType;
import cn.devicelinks.common.AttributeScope;
import cn.devicelinks.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备属性
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceAttribute implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String deviceId;
    private String moduleId;
    private String attributeId;
    private String identifier;
    private AttributeDataType dataType;
    private AttributeScope scope;
    private Object value;
    private int version;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime createTime;
}
