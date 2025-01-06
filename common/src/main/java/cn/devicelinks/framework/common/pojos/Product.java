package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 产品基本信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String name;
    private String productKey;
    private String productSecret;
    private DeviceNetworkingAway networkingAway;
    private DataFormat dataFormat;
    private DeviceAuthenticationMethod authenticationMethod;
    private String signatureCode;
    private ProductStatus status;
    private boolean deleted;
    private String description;
    private LocalDateTime createTime;
}
