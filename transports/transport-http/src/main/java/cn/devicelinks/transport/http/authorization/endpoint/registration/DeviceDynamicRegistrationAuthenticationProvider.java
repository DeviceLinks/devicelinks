package cn.devicelinks.transport.http.authorization.endpoint.registration;

import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.device.center.DeviceProfileFeignClient;
import cn.devicelinks.api.device.center.ProductFeignClient;
import cn.devicelinks.api.device.center.model.request.DynamicRegistrationRequest;
import cn.devicelinks.api.device.center.model.response.DynamicRegistrationResponse;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.DeviceProvisionStrategy;
import cn.devicelinks.common.DynamicRegistrationMethod;
import cn.devicelinks.common.utils.TimeUtils;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.component.web.utils.SignUtils;
import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.entity.DeviceProfileProvisionAddition;
import cn.devicelinks.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import static cn.devicelinks.api.support.StatusCodeConstants.REQUEST_EXPIRED;
import static cn.devicelinks.api.support.StatusCodeConstants.SIGN_VERIFICATION_FAILED;

/**
 * 设备动态注册认证提供者
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class DeviceDynamicRegistrationAuthenticationProvider implements AuthenticationProvider {
    private static final Long EFFECTIVE_SECONDS = 10L;
    private final DeviceFeignClient deviceFeignClient;
    private final DeviceProfileFeignClient deviceProfileFeignClient;
    private final ProductFeignClient productFeignClient;

    public DeviceDynamicRegistrationAuthenticationProvider(DeviceFeignClient deviceFeignClient,
                                                           DeviceProfileFeignClient deviceProfileFeignClient,
                                                           ProductFeignClient productFeignClient) {
        this.deviceFeignClient = deviceFeignClient;
        this.deviceProfileFeignClient = deviceProfileFeignClient;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DeviceDynamicRegistrationAuthenticationToken authenticationRequestToken = (DeviceDynamicRegistrationAuthenticationToken) authentication;
        DynamicRegistrationMethod registrationMethod = authenticationRequestToken.getRegistrationMethod();
        // Check if it is expired
        if (!TimeUtils.validateTimestamp(authenticationRequestToken.getTimestamp(), EFFECTIVE_SECONDS * 1000)) {
            throw new DeviceLinksAuthorizationException(REQUEST_EXPIRED);
        }
        String secret;
        // @formatter:off
        DynamicRegistrationRequest registrationRequest =
                new DynamicRegistrationRequest()
                        .setRegistrationMethod(registrationMethod.toString())
                        .setDeviceName(authenticationRequestToken.getDeviceName());
        // @formatter:on
        if (DynamicRegistrationMethod.ProvisionKey == registrationMethod) {
            DeviceProfile deviceProfile = ApiResponseUnwrapper.unwrap(deviceProfileFeignClient.getByProvisionKey(authenticationRequestToken.getProvisionKey()));
            if (deviceProfile == null || deviceProfile.isDeleted()) {
                throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, authenticationRequestToken.getProvisionKey());
            }
            DeviceProfileProvisionAddition profileProvisionAddition = deviceProfile.getProvisionAddition();
            if (profileProvisionAddition == null || profileProvisionAddition.getStrategy() == null || DeviceProvisionStrategy.AllowCreateDevice != profileProvisionAddition.getStrategy()) {
                throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_PROFILE_CREATION_NOT_ALLOWED, authenticationRequestToken.getProvisionKey());
            }
            secret = profileProvisionAddition.getProvisionDeviceSecret();
            registrationRequest.setProductId(deviceProfile.getProductId()).setProfileId(deviceProfile.getId());
        } else if (DynamicRegistrationMethod.ProductKey == registrationMethod) {
            Product product = ApiResponseUnwrapper.unwrap(productFeignClient.getByProductKey(authenticationRequestToken.getProductKey()));
            if (product == null || product.isDeleted()) {
                throw new DeviceLinksAuthorizationException(StatusCodeConstants.PRODUCT_NOT_EXISTS, authenticationRequestToken.getProductKey());
            }
            secret = product.getProductSecret();
            registrationRequest.setProductId(product.getId());
        } else {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_DYNAMIC_REGISTRATION_METHOD_NOT_SUPPORT);
        }
        // Check the signature
        try {
            String sign = SignUtils.sign(secret,
                    String.valueOf(authenticationRequestToken.getTimestamp()), authenticationRequestToken.getRequest());
            log.debug("Signature Verification, request sign：{}, sign：{}.", authenticationRequestToken.getSign(), sign);
            if (ObjectUtils.isEmpty(authenticationRequestToken.getSign()) || !sign.equals(authenticationRequestToken.getSign())) {
                throw new DeviceLinksAuthorizationException(SIGN_VERIFICATION_FAILED);
            }
        } catch (Exception e) {
            throw new DeviceLinksAuthorizationException(SIGN_VERIFICATION_FAILED);
        }
        // Invoke Dynamic Registration
        DynamicRegistrationResponse registrationResponse = ApiResponseUnwrapper.unwrap(deviceFeignClient.dynamicRegistration(registrationRequest));
        return DeviceDynamicRegistrationAuthenticationToken.authenticated(registrationResponse.getDeviceId(), registrationResponse.getDeviceName(), registrationResponse.getDeviceSecret());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DeviceDynamicRegistrationAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
