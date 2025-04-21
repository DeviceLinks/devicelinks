package cn.devicelinks.transport.http.authorization.endpoint.credentials;

import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.support.feign.FeignClientSignUtils;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.api.ApiResponseUnwrapper;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import static cn.devicelinks.api.support.StatusCodeConstants.*;

/**
 * 设备动态令牌颁发认证器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceDynamicTokenIssuedAuthenticationProvider implements AuthenticationProvider {

    private static final Long EFFECTIVE_SECONDS = 20L;

    private final DeviceFeignClient deviceFeignClient;

    private final DeviceCredentialsFeignClient deviceCredentialsFeignClient;

    public DeviceDynamicTokenIssuedAuthenticationProvider(DeviceFeignClient deviceFeignClient,
                                                          DeviceCredentialsFeignClient deviceCredentialsFeignClient) {
        this.deviceFeignClient = deviceFeignClient;
        this.deviceCredentialsFeignClient = deviceCredentialsFeignClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DeviceDynamicTokenIssuedAuthenticationToken authenticationToken = (DeviceDynamicTokenIssuedAuthenticationToken) authentication;
        Device device = ApiResponseUnwrapper.unwrap(this.deviceFeignClient.getDeviceById(authenticationToken.getDeviceId()));
        if (device == null || device.isDeleted()) {
            throw new DeviceLinksAuthorizationException(UNKNOWN_DEVICE);
        }
        if (!device.isEnabled()) {
            throw new DeviceLinksAuthorizationException(DEVICE_DISABLED);
        }
        if (!authenticationToken.getDeviceName().equals(device.getDeviceName())) {
            throw new DeviceLinksAuthorizationException(DEVICE_NAME_NOT_MATCH);
        }
        long requestTimestamp = Long.parseLong(authenticationToken.getTimestamp());
        long currentTimestamp = System.currentTimeMillis();
        if (requestTimestamp <= Constants.ZERO || currentTimestamp - requestTimestamp > EFFECTIVE_SECONDS * 1000) {
            throw new DeviceLinksAuthorizationException(REQUEST_EXPIRED);
        }
        try {
            String decryptedSecret = ApiResponseUnwrapper.unwrap(deviceFeignClient.decryptDeviceSecret(device.getId()));
            String sign = FeignClientSignUtils.sign(decryptedSecret, authenticationToken.getTimestamp(), authenticationToken.getRequest());
            if (ObjectUtils.isEmpty(authenticationToken.getSign()) || !sign.equals(authenticationToken.getSign())) {
                throw new DeviceLinksAuthorizationException(SIGN_VERIFICATION_FAILED);
            }
        } catch (Exception e) {
            throw new DeviceLinksAuthorizationException(SIGN_VERIFICATION_FAILED);
        }
        DeviceCredentials deviceCredentials = ApiResponseUnwrapper.unwrap(deviceCredentialsFeignClient.generateDynamicToken(device.getId()));
        // @formatter:off
        return DeviceDynamicTokenIssuedAuthenticationToken.authenticated(deviceCredentials.getDeviceId(),
                deviceCredentials.getAddition().getToken(),
                deviceCredentials.getExpirationTime()
        );
        // @formatter:on
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DeviceDynamicTokenIssuedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
