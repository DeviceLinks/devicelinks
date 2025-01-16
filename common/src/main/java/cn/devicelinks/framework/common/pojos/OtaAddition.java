package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * OTA附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class OtaAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    //...
}
