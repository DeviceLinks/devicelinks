package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知规则
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class NotificationRule implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String name;
    private String triggerTypeId;
    private String templateId;
    private List<String> receiverIds;
    private NotificationRuleAddition addition;
    private boolean enabled;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
    private String description;
}
