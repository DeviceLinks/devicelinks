package cn.devicelinks.entity;

import cn.devicelinks.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备属性创建白名单
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DeviceAttributeCreateWhitelist implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String productId;
    private String moduleId;
    private String identifier;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
}
