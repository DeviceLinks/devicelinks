package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通知模版附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class NotificationTemplateAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    //...
}
