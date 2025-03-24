package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.ProvisionRegistrationStrategy;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备配置文件 - 预注册配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceProfileProvisionRegistrationAddition {
    private ProvisionRegistrationStrategy strategy;
    private String deviceKey;
    private String deviceSecret;
    private boolean allowCreateDeviceByX509Certificate;
    private String certificateRegExPattern;
    private String x509Pem;
}
