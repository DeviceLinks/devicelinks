package cn.devicelinks.center.authorization.endpoint.api;

import cn.devicelinks.api.support.feign.FeignClientSignUtils;
import cn.devicelinks.center.configuration.DeviceCenterProperties;
import cn.devicelinks.common.Constants;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.devicelinks.api.support.StatusCodeConstants.*;

/**
 * Api访问认证处理逻辑提供者
 * <p>
 * 验证ApiKey有效性
 * 根据ApiSecret、Timestamp验证Sign是否正确
 * 验证时间戳（Timestamp）与当前时间的间隔是否过大
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class ApiAccessAuthenticationProvider implements AuthenticationProvider {

    private static final Long EFFECTIVE_SECONDS = 20L;

    private final Map<String, DeviceCenterProperties.InternalServiceApiKey> apiKeyMap;

    public ApiAccessAuthenticationProvider(List<DeviceCenterProperties.InternalServiceApiKey> apiKeys) {
        Assert.notEmpty(apiKeys, "The ApiKeys cannot empty.");
        this.apiKeyMap = apiKeys.stream().collect(Collectors.toMap(DeviceCenterProperties.InternalServiceApiKey::getApiKey, v -> v));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiAccessAuthenticationToken apiAccessAuthenticationToken = (ApiAccessAuthenticationToken) authentication;
        if (!apiKeyMap.containsKey(apiAccessAuthenticationToken.getApiKey())) {
            throw new DeviceLinksAuthorizationException(API_KEY_NOT_FOUND);
        }
        DeviceCenterProperties.InternalServiceApiKey internalServiceApiKey = this.apiKeyMap.get(apiAccessAuthenticationToken.getApiKey());
        try {
            String sign = FeignClientSignUtils.sign(internalServiceApiKey.getApiSecret(), String.valueOf(apiAccessAuthenticationToken.getTimestamp()),
                    apiAccessAuthenticationToken.getRequest());
            if (ObjectUtils.isEmpty(apiAccessAuthenticationToken.getSign()) || !sign.equals(apiAccessAuthenticationToken.getSign())) {
                throw new DeviceLinksAuthorizationException(API_KEY_SIGN_ERROR);
            }
        } catch (Exception e) {
            throw new DeviceLinksAuthorizationException(API_KEY_SIGN_ERROR);
        }
        Long currentTimestamp = System.currentTimeMillis();
        if (apiAccessAuthenticationToken.getTimestamp() <= Constants.ZERO || currentTimestamp - apiAccessAuthenticationToken.getTimestamp() > EFFECTIVE_SECONDS * 1000) {
            throw new DeviceLinksAuthorizationException(API_REQUEST_IS_EXPIRED);
        }
        // Verification passed
        apiAccessAuthenticationToken.setAuthenticated(true);
        return apiAccessAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAccessAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
