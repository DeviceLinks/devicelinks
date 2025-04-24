package cn.devicelinks.api.device.center;

import cn.devicelinks.api.device.center.response.DecryptTokenResponse;
import cn.devicelinks.api.support.feign.FeignConstants;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.DeviceCredentials;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 设备秘钥Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceCredentialsFeignClient {
    /**
     * 根据令牌查询设备凭证信息
     *
     * @param tokenHash 令牌Hash值，非明文令牌
     * @return 设备凭证信息 {@link DeviceCredentials}
     * @see DeviceCredentialsType#DynamicToken
     * @see DeviceCredentialsType#StaticToken
     */
    @RequestLine("GET /api/device/credentials?tokenHash={tokenHash}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DeviceCredentials> selectByTokenHash(@Param("tokenHash") String tokenHash);

    /**
     * 令牌解密
     *
     * @param tokenHash 令牌哈希值
     * @return 解密后的令牌
     */
    @RequestLine("GET /api/device/credentials/token-decrypt?tokenHash={tokenHash}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DecryptTokenResponse> decryptToken(@Param("tokenHash") String tokenHash);

    /**
     * 生成动态令牌
     *
     * @return 动态令牌凭据 {@link DeviceCredentials}
     */
    @RequestLine("POST /api/device/credentials/generate-dynamic-token?deviceId={deviceId}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DeviceCredentials> generateDynamicToken(@Param("deviceId") String deviceId);
}
