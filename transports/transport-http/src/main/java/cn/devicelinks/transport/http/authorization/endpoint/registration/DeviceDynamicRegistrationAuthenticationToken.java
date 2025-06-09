package cn.devicelinks.transport.http.authorization.endpoint.registration;

import cn.devicelinks.common.DynamicRegistrationMethod;
import cn.devicelinks.common.utils.HmacSignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 设备动态注册认证令牌
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class DeviceDynamicRegistrationAuthenticationToken extends AbstractAuthenticationToken {
    private HttpServletRequest request;
    private DynamicRegistrationMethod registrationMethod;
    private String provisionKey;
    private String productKey;
    private final String deviceName;
    private long timestamp;
    private HmacSignatureAlgorithm signAlgorithm;
    private String sign;

    private String deviceId;
    private String deviceSecret;

    private DeviceDynamicRegistrationAuthenticationToken(HttpServletRequest request, DynamicRegistrationMethod registrationMethod,
                                                         String provisionKey,
                                                         String productKey,
                                                         String deviceName, long timestamp, HmacSignatureAlgorithm signAlgorithm, String sign) {
        super(Collections.emptyList());
        this.request = request;
        this.registrationMethod = registrationMethod;
        this.provisionKey = provisionKey;
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.timestamp = timestamp;
        this.signAlgorithm = signAlgorithm;
        this.sign = sign;
        super.setAuthenticated(false);
    }

    private DeviceDynamicRegistrationAuthenticationToken(String deviceId, String deviceName, String deviceSecret) {
        super(Collections.emptyList());
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        super.setAuthenticated(true);
    }

    public static DeviceDynamicRegistrationAuthenticationToken unauthenticated(HttpServletRequest request, DynamicRegistrationMethod registrationMethod,
                                                                               String provisionKey, String productKey,
                                                                               String deviceName, long timestamp, HmacSignatureAlgorithm signAlgorithm, String sign) {
        return new DeviceDynamicRegistrationAuthenticationToken(request, registrationMethod, provisionKey, productKey, deviceName, timestamp, signAlgorithm, sign);
    }

    public static DeviceDynamicRegistrationAuthenticationToken authenticated(String deviceId, String deviceName, String deviceSecret) {
        return new DeviceDynamicRegistrationAuthenticationToken(deviceId, deviceName, deviceSecret);
    }

    @Override
    public Object getCredentials() {
        return sign;
    }

    @Override
    public Object getPrincipal() {
        return deviceName;
    }
}
