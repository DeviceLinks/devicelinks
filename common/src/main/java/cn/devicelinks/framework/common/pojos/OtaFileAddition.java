package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class OtaFileAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    //...
}
