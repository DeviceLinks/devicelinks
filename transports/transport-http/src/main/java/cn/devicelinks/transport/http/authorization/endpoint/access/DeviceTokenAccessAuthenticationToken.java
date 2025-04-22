package cn.devicelinks.transport.http.authorization.endpoint.access;

import cn.devicelinks.entity.Device;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 设备携带令牌认证对象
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class DeviceTokenAccessAuthenticationToken extends AbstractAuthenticationToken {
    private final String accessToken;
    private Device device;

    public DeviceTokenAccessAuthenticationToken(String accessToken) {
        super(Collections.emptyList());
        this.accessToken = accessToken;
    }

    public DeviceTokenAccessAuthenticationToken(String accessToken, Device device) {
        super(Collections.emptyList());
        this.accessToken = accessToken;
        this.device = device;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getPrincipal() {
        return device;
    }

    public static DeviceTokenAccessAuthenticationToken unauthenticated(String accessToken) {
        return new DeviceTokenAccessAuthenticationToken(accessToken);
    }

    public static DeviceTokenAccessAuthenticationToken authenticated(String token, Device device) {
        DeviceTokenAccessAuthenticationToken resourceTokenAccessAuthenticationToken = new DeviceTokenAccessAuthenticationToken(token, device);
        resourceTokenAccessAuthenticationToken.setAuthenticated(true);
        return resourceTokenAccessAuthenticationToken;
    }
}
