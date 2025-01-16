package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.NotificationSeverity;
import cn.devicelinks.framework.common.NotificationStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Notification implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String departmentId;
    private String typeId;
    private String subject;
    private String message;
    private NotificationStatus status;
    private NotificationSeverity severity;
    private NotificationAddition addition;
    private LocalDateTime createTime;
}
