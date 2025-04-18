package cn.devicelinks.transport.http.authorization.endpoint.credentials;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.framework.common.feign.ApiRequestSignUtils;
import cn.devicelinks.framework.common.feign.DeviceCenterDeviceFeignApi;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.utils.SecureRandomUtils;
import cn.devicelinks.service.device.DeviceCredentialsService;
import cn.devicelinks.service.device.DeviceService;
import cn.devicelinks.transport.http.configuration.TransportHttpProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * 设备动态令牌颁发认证器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceDynamicTokenIssuedAuthenticationProvider implements AuthenticationProvider {

    private static final Long EFFECTIVE_TIMESTAMP = 120L;

    private static final StatusCode UNKNOWN_DEVICE = StatusCode.build("UNKNOWN_DEVICE", "未知的设备.");

    private static final StatusCode DEVICE_DISABLED = StatusCode.build("DEVICE_DISABLED", "设备已被禁用.");

    private static final StatusCode DEVICE_NAME_NOT_MATCH = StatusCode.build("DEVICE_NAME_NOT_MATCH", "设备名称不匹配.");

    private static final StatusCode TIMESTAMP_EXPIRED = StatusCode.build("TIMESTAMP_EXPIRED", "请求时间戳已过期，请求时间戳与当前时间有效最大间隔为：" + EFFECTIVE_TIMESTAMP + " 秒.");

    private static final StatusCode SIGN_VERIFICATION_FAILED = StatusCode.build("SIGN_VERIFICATION_FAILED", "签名校验失败.");

    private static final StatusCode DEVICE_SECRET_INVALID = StatusCode.build("DEVICE_SECRET_INVALID", "无效的设备密钥.");

    private final DeviceService deviceService;

    private final DeviceCredentialsService deviceCredentialsService;

    private final DeviceCenterDeviceFeignApi deviceCenterDeviceFeignApi;

    private final TransportHttpProperties.TokenSetting tokenSetting;

    public DeviceDynamicTokenIssuedAuthenticationProvider(DeviceService deviceService,
                                                          DeviceCredentialsService deviceCredentialsService,
                                                          DeviceCenterDeviceFeignApi deviceCenterDeviceFeignApi,
                                                          TransportHttpProperties.TokenSetting tokenSetting) {
        this.deviceService = deviceService;
        this.deviceCredentialsService = deviceCredentialsService;
        this.deviceCenterDeviceFeignApi = deviceCenterDeviceFeignApi;
        this.tokenSetting = tokenSetting;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DeviceDynamicTokenIssuedAuthenticationToken authenticationToken = (DeviceDynamicTokenIssuedAuthenticationToken) authentication;
        Device device = this.deviceService.selectById(authenticationToken.getDeviceId());
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
        if (requestTimestamp <= Constants.ZERO || currentTimestamp - requestTimestamp > EFFECTIVE_TIMESTAMP) {
            throw new DeviceLinksAuthorizationException(TIMESTAMP_EXPIRED);
        }
        try {
            ApiResponse<String> apiResponse = deviceCenterDeviceFeignApi.decryptDeviceSecret(device.getId());
            if (!StatusCode.SUCCESS.getCode().equals(apiResponse.getCode())) {
                throw new DeviceLinksAuthorizationException(DEVICE_SECRET_INVALID);
            }
            String decryptedSecret = apiResponse.getData();
            String sign = ApiRequestSignUtils.sign(decryptedSecret, authenticationToken.getTimestamp(), authenticationToken.getRequest());
            if (ObjectUtils.isEmpty(authenticationToken.getSign()) || !sign.equals(authenticationToken.getSign())) {
                throw new DeviceLinksAuthorizationException(SIGN_VERIFICATION_FAILED);
            }
        } catch (Exception e) {
            throw new DeviceLinksAuthorizationException(SIGN_VERIFICATION_FAILED);
        }
        // Save DynamicToken
        String dynamicToken = SecureRandomUtils.generateRandomHex(tokenSetting.getIssuedDynamicTokenLength());
        LocalDateTime tokenExpirationTime = LocalDateTime.now().plusSeconds(tokenSetting.getValiditySeconds());
        deviceCredentialsService.addDynamicToken(device.getId(), dynamicToken, tokenExpirationTime);
        return DeviceDynamicTokenIssuedAuthenticationToken.authenticated(device.getId(), dynamicToken, tokenExpirationTime);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DeviceDynamicTokenIssuedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
