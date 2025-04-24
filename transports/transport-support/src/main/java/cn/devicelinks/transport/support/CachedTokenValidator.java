package cn.devicelinks.transport.support;

import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.device.center.response.DecryptTokenResponse;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.utils.DigestUtils;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import static cn.devicelinks.api.support.StatusCodeConstants.TOKEN_EXPIRED;
import static cn.devicelinks.api.support.StatusCodeConstants.TOKEN_INVALID;

/**
 * 支持缓存的令牌验证器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class CachedTokenValidator implements TokenValidationService {

    @Autowired
    private DeviceCredentialsFeignClient deviceCredentialsFeignClient;

    /**
     * 令牌缓存
     */
    private final LoadingCache<String, DecryptTokenResponse> TOKEN_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .maximumSize(100_000)
            .build(tokenHash -> ApiResponseUnwrapper.unwrap(deviceCredentialsFeignClient.decryptToken(tokenHash)));

    @Override
    public TokenValidationResponse validationToken(String token) throws ApiException {
        String tokenHash = DigestUtils.getHexDigest(cn.devicelinks.common.utils.DigestUtils.SHA256, token.getBytes(StandardCharsets.UTF_8));
        DecryptTokenResponse decryptTokenResponse = TOKEN_CACHE.get(tokenHash);
        // If it is a dynamic token, verify the validity period
        if (DeviceCredentialsType.DynamicToken == decryptTokenResponse.getCredentialsType() && decryptTokenResponse.getExpirationTime() != null) {
            if (LocalDateTime.now().isAfter(decryptTokenResponse.getExpirationTime())) {
                throw new ApiException(TOKEN_EXPIRED);
            }
        }
        if (!token.equals(decryptTokenResponse.getToken())) {
            throw new ApiException(TOKEN_INVALID);
        }
        // @formatter:off
        return new TokenValidationResponse(
                decryptTokenResponse.getCredentialsType(),
                decryptTokenResponse.getExpirationTime(),
                decryptTokenResponse.getDeviceId()
        );
        // @formatter:on
    }
}
