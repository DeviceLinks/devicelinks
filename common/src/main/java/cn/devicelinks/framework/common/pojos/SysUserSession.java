package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.PlatformType;
import cn.devicelinks.framework.common.SessionStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户会话
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class SysUserSession implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String id;
    private String userId;
    private String username;
    private String tokenValue;
    private String ipAddress;
    private PlatformType platformType;
    private SessionStatus status;
    private LocalDateTime issuedTime;
    private LocalDateTime expiresTime;
    private LocalDateTime logoutTime;
    private LocalDateTime lastActiveTime;
}
