package cn.devicelinks.transport.http.authorization.endpoint.access;

import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.DeviceStatus;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.entity.Device;
import cn.devicelinks.transport.support.TokenValidationResponse;
import cn.devicelinks.transport.support.TokenValidationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import static cn.devicelinks.api.support.StatusCodeConstants.*;

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

    private final DeviceFeignClient deviceFeignClient;
    private final TokenValidationService tokenValidationService;

    public DeviceTokenAccessAuthenticationProvider(DeviceFeignClient deviceFeignClient, TokenValidationService tokenValidationService) {
        this.deviceFeignClient = deviceFeignClient;
        this.tokenValidationService = tokenValidationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DeviceTokenAccessAuthenticationToken authenticationToken = (DeviceTokenAccessAuthenticationToken) authentication;
        TokenValidationResponse validationResponse;
        try {
            validationResponse = tokenValidationService.validationToken(authenticationToken.getAccessToken());
        } catch (ApiException e) {
            throw new DeviceLinksAuthorizationException(e.getStatusCode());
        }
        // Checking device validity
        Device device = ApiResponseUnwrapper.unwrap(this.deviceFeignClient.getDeviceById(validationResponse.getDeviceId()));
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
