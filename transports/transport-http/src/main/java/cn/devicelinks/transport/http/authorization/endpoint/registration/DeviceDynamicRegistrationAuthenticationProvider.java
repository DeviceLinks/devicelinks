package cn.devicelinks.transport.http.authorization.endpoint.registration;

import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.device.center.DeviceProfileFeignClient;
import cn.devicelinks.api.device.center.ProductFeignClient;
import cn.devicelinks.api.device.center.model.request.DynamicRegistrationRequest;
import cn.devicelinks.api.device.center.model.response.DynamicRegistrationResponse;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.DeviceProvisionStrategy;
import cn.devicelinks.common.DeviceType;
import cn.devicelinks.common.DynamicRegistrationMethod;
import cn.devicelinks.common.utils.TimeUtils;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.component.web.utils.SignUtils;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.entity.Device;
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
        // @formatter:off
        DynamicRegistrationRequest registrationRequest =
                new DynamicRegistrationRequest()
                        .setDeviceName(authenticationRequestToken.getDeviceName())
                        .setDeviceType(DeviceType.Direct.toString());
        // @formatter:on
        if (DynamicRegistrationMethod.ProvisionKey == registrationMethod) {
            validateProvisionData(authenticationRequestToken, registrationRequest);
        } else if (DynamicRegistrationMethod.ProductKey == registrationMethod) {
            validateProductKeyData(authenticationRequestToken, registrationRequest);
        } else {
            throw new DeviceLinksAuthorizationException("不支持的动态注册方式，[" + registrationMethod + "].");
        }
        // Invoke Dynamic Registration
        DynamicRegistrationResponse registrationResponse = ApiResponseUnwrapper.unwrap(deviceFeignClient.dynamicRegistration(registrationRequest));
        return DeviceDynamicRegistrationAuthenticationToken.authenticated(registrationResponse.getDeviceId(), registrationResponse.getDeviceName(), registrationResponse.getDeviceSecret());
    }

    /**
     * 预配置动态注册方式数据验证
     *
     * @param authenticationRequestToken {@link DeviceDynamicRegistrationAuthenticationToken}
     * @param registrationRequest        设备动态注册请求实体 {@link DynamicRegistrationRequest}
     */
    private void validateProvisionData(DeviceDynamicRegistrationAuthenticationToken authenticationRequestToken, DynamicRegistrationRequest registrationRequest) {
        DeviceProfile deviceProfile = ApiResponseUnwrapper.unwrap(deviceProfileFeignClient.getByProvisionKey(authenticationRequestToken.getProvisionKey()));
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, authenticationRequestToken.getProvisionKey());
        }
        DeviceProfileProvisionAddition profileProvisionAddition = deviceProfile.getProvisionAddition();
        if (profileProvisionAddition == null || profileProvisionAddition.getStrategy() == null || DeviceProvisionStrategy.AllowCreateDevice != profileProvisionAddition.getStrategy()) {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_PROFILE_CREATION_NOT_ALLOWED, authenticationRequestToken.getProvisionKey());
        }
        if (ObjectUtils.isEmpty(deviceProfile.getProductId())) {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_PROFILE_NOT_HAVE_PRODUCT_ID, deviceProfile.getId());
        }
        Product product = ApiResponseUnwrapper.unwrap(productFeignClient.getByProductId(deviceProfile.getProductId()));
        this.commonValidate(authenticationRequestToken, product, profileProvisionAddition.getProvisionDeviceSecret());
        registrationRequest.setProductId(product.getId()).setProfileId(deviceProfile.getId());
    }

    /**
     * 产品密钥动态注册方式数据验证
     *
     * @param authenticationRequestToken {@link DeviceDynamicRegistrationAuthenticationToken}
     * @param registrationRequest        设备动态注册请求实体 {@link DynamicRegistrationRequest}
     * @see DynamicRegistrationMethod#ProductKey
     */
    private void validateProductKeyData(DeviceDynamicRegistrationAuthenticationToken authenticationRequestToken, DynamicRegistrationRequest registrationRequest) {
        Product product = ApiResponseUnwrapper.unwrap(productFeignClient.getByProductKey(authenticationRequestToken.getProductKey()));
        this.commonValidate(authenticationRequestToken, product, product.getProductSecret());
        registrationRequest.setProductId(product.getId());
    }

    private void commonValidate(DeviceDynamicRegistrationAuthenticationToken authenticationRequestToken, Product product, String secret) {
        if (product == null || product.isDeleted()) {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.PRODUCT_NOT_EXISTS, authenticationRequestToken.getProductKey());
        }
        if (!product.isDynamicRegistration()) {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.PRODUCT_NOT_SUPPORTED_DYNAMIC_REGISTER, authenticationRequestToken.getProductKey());
        }
        Device device = ApiResponseUnwrapper.unwrap(deviceFeignClient.getDeviceByName(authenticationRequestToken.getDeviceName()));
        if (device != null && (device.isEnabled() || !device.isDeleted())) {
            throw new DeviceLinksAuthorizationException(StatusCodeConstants.DEVICE_ALREADY_EXISTS, authenticationRequestToken.getDeviceName());
        }
        if (!TimeUtils.validateTimestamp(authenticationRequestToken.getTimestamp(), EFFECTIVE_SECONDS * 1000)) {
            throw new DeviceLinksAuthorizationException(REQUEST_EXPIRED);
        }
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
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DeviceDynamicRegistrationAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
