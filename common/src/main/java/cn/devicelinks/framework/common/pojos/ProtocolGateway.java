package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 协议网关
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class ProtocolGateway implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String name;
    private String productId;
    private GatewayProtocol protocol;
    private int port;
    private String url;
    private GatewayAuthenticationMethod authenticationMethod;
    private String authenticationUrl;
    private DataTransportProtocol transportProtocol;
    private String serverCa;
    private String serverCaKey;
    private GatewayStatus status;
    private String containerId;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
}
