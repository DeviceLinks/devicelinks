package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.AttributeValueSource;
import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备属性最新值
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceAttributeLatest implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String deviceId;
    private String moduleId;
    private String attributeId;
    private String identifier;
    private Object value;
    private AttributeValueSource valueSource;
    private int version;
    private boolean displayOnStatusPage;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime createTime;
}
