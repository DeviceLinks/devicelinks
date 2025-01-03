package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.CommandStatus;
import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 指令
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String deviceId;
    private String content;
    private Map<String, Object> params;
    private CommandStatus status;
    private LocalDateTime sendTime;
    private LocalDateTime responseTime;
    private String createBy;
    private LocalDateTime createTime;
    private String failureReason;
}
