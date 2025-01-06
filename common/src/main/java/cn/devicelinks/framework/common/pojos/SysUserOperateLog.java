package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.OperateAction;
import cn.devicelinks.framework.common.OperateObjectType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户操作日志
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class SysUserOperateLog implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String userId;
    private String requestId;
    private String resourceCode;
    private OperateAction action;
    private OperateObjectType objectType;
    private String object;
    private String objectFields;
    private String msg;
    private boolean success;
    private String failureReason;
    private String ipAddress;
    private Map<String, Object> addition;
    private LocalDateTime operateTime;
}
