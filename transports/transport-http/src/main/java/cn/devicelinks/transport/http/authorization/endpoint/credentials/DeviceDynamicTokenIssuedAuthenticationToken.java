package cn.devicelinks.transport.http.authorization.endpoint.credentials;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 设备动态令牌颁发认证令牌
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class DeviceDynamicTokenIssuedAuthenticationToken extends AbstractAuthenticationToken {
    private HttpServletRequest request;
    private final String deviceId;
    private String deviceName;
    private String timestamp;
    private String sign;
    private String token;
    private LocalDateTime expiresAt;

    public DeviceDynamicTokenIssuedAuthenticationToken(HttpServletRequest request, String deviceId, String deviceName, String timestamp, String sign) {
        super(Collections.emptyList());
        this.request = request;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.timestamp = timestamp;
        this.sign = sign;
        this.setAuthenticated(false);
    }

    public DeviceDynamicTokenIssuedAuthenticationToken(String deviceId, String token, LocalDateTime expiresAt) {
        super(Collections.emptyList());
        this.deviceId = deviceId;
        this.token = token;
        this.expiresAt = expiresAt;
        super.setAuthenticated(true);
    }

    public static DeviceDynamicTokenIssuedAuthenticationToken unauthenticated(HttpServletRequest request, String deviceId, String deviceName, String timestamp, String sign) {
        return new DeviceDynamicTokenIssuedAuthenticationToken(request, deviceId, deviceName, timestamp, sign);
    }

    public static DeviceDynamicTokenIssuedAuthenticationToken authenticated(String deviceId, String token, LocalDateTime expiresAt) {
        return new DeviceDynamicTokenIssuedAuthenticationToken(deviceId, token, expiresAt);
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.deviceId;
    }

    @Override
    public Object getPrincipal() {
        return this.deviceId;
    }
}
