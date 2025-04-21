package cn.devicelinks.api.device.center;

import cn.devicelinks.api.support.feign.FeignConstants;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.secret.AesSecretKeySet;
import feign.Headers;
import feign.RequestLine;

/**
 * 公共通用接口Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface CommonFeignClient {
    /**
     * 获取AES加密Keys集合
     *
     * @return {@link AesSecretKeySet}
     */
    @RequestLine("GET /api/common/aes-secret-keys")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<AesSecretKeySet> getAesSecretKeys();
}
