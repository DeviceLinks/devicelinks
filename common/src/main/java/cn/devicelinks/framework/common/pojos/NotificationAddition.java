package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.LogObjectType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通知附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class NotificationAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private DeviceLifecycle deviceLifecycle;

    private DataEntityAction dataEntityAction;

    /**
     * @see cn.devicelinks.framework.common.NotificationTypeIdentifier#DataEntityAction
     */
    @Data
    @Accessors(chain = true)
    public static class DataEntityAction {
        private LogObjectType objectType;
        private String objectId;
    }

    /**
     * @see cn.devicelinks.framework.common.NotificationTypeIdentifier#DeviceLifecycle
     */
    @Data
    @Accessors(chain = true)
    public static class DeviceLifecycle {
        private String deviceId;
    }
}
