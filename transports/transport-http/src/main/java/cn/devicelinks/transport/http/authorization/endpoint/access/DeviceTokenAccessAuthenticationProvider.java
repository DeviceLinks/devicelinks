package cn.devicelinks.transport.http.authorization.endpoint.access;

import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.DeviceStatus;
import cn.devicelinks.framework.common.api.ApiResponseUnwrapper;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * 设备携令牌访问资源认证提供器
 * <p>
 * 支持认证静态令牌{@link DeviceCredentialsType#StaticToken}、
 * 动态令牌{@link DeviceCredentialsType#DynamicToken}
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceTokenAccessAuthenticationProvider implements AuthenticationProvider {

    private static final StatusCode TOKEN_INVALID = StatusCode.build("TOKEN_INVALID", "无效的令牌.");
    private static final StatusCode TOKEN_EXPIRED = StatusCode.build("TOKEN_EXPIRED", "令牌已过期，请重新获取令牌后再次请求.");
    private static final StatusCode UNKNOWN_DEVICE = StatusCode.build("UNKNOWN_DEVICE", "未知的设备.");
    private static final StatusCode DEVICE_DISABLED = StatusCode.build("DEVICE_DISABLED", "设备已被禁用.");
    private static final StatusCode DEVICE_ACTIVATE_FAIL = StatusCode.build("DEVICE_ACTIVATE_FAIL", "设备激活失败.");

    private final DeviceFeignClient deviceFeignClient;
    private final DeviceCredentialsFeignClient deviceCredentialsFeignClient;

    public DeviceTokenAccessAuthenticationProvider(DeviceFeignClient deviceFeignClient, DeviceCredentialsFeignClient deviceCredentialsFeignClient) {
        this.deviceFeignClient = deviceFeignClient;
        this.deviceCredentialsFeignClient = deviceCredentialsFeignClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DeviceTokenAccessAuthenticationToken authenticationToken = (DeviceTokenAccessAuthenticationToken) authentication;
        // Checking token credential validity
        DeviceCredentials deviceCredentials = ApiResponseUnwrapper.unwrap(this.deviceCredentialsFeignClient.selectByToken(authenticationToken.getAccessToken()));
        if (deviceCredentials == null || deviceCredentials.isDeleted()) {
            throw new DeviceLinksAuthorizationException(TOKEN_INVALID);
        }
        // If it is a dynamic token, verify the validity period
        if (DeviceCredentialsType.DynamicToken == deviceCredentials.getCredentialsType() && deviceCredentials.getExpirationTime() != null) {
            if (LocalDateTime.now().isAfter(deviceCredentials.getExpirationTime())) {
                throw new DeviceLinksAuthorizationException(TOKEN_EXPIRED);
            }
        }
        // Checking device validity
        Device device = ApiResponseUnwrapper.unwrap(this.deviceFeignClient.getDeviceById(deviceCredentials.getDeviceId()));
        if (device == null || device.isDeleted()) {
            throw new DeviceLinksAuthorizationException(UNKNOWN_DEVICE);
        }
        if (!device.isEnabled()) {
            throw new DeviceLinksAuthorizationException(DEVICE_DISABLED);
        }
        // Activate the device
        if (DeviceStatus.NotActivate == device.getStatus() && ObjectUtils.isEmpty(device.getActivationTime())) {
            Boolean activateSuccess = ApiResponseUnwrapper.unwrap(this.deviceFeignClient.activateDevice(device.getId()));
            if (!activateSuccess) {
                throw new DeviceLinksAuthorizationException(DEVICE_ACTIVATE_FAIL);
            }
        }
        return DeviceTokenAccessAuthenticationToken.authenticated(authenticationToken.getAccessToken(), device);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DeviceTokenAccessAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
