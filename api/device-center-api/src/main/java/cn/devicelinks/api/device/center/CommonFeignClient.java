package cn.devicelinks.api.device.center;

import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.openfeign.cache.FeignCacheable;
import cn.devicelinks.component.web.api.ApiResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * 公共通用接口Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface CommonFeignClient {
    /**
     * 获取AES加密Keys集合
     *
     * @return {@link AesSecretKeySet}
     */
    @RequestLine("GET /api/common/aes-secret-keys")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    @FeignCacheable
    ApiResponse<AesSecretKeySet> getAesSecretKeys();
}
