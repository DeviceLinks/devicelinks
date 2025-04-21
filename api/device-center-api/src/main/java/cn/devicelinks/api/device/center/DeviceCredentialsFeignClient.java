package cn.devicelinks.api.device.center;

import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.api.support.feign.FeignConstants;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
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
     * @param token 令牌，动态令牌或者静态令牌
     * @return 设备凭证信息 {@link DeviceCredentials}
     * @see cn.devicelinks.framework.common.DeviceCredentialsType#DynamicToken
     * @see cn.devicelinks.framework.common.DeviceCredentialsType#StaticToken
     */
    @RequestLine("GET /api/device/credentials?token={token}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DeviceCredentials> selectByToken(@Param("token") String token);

    /**
     * 生成动态令牌
     *
     * @return 动态令牌凭据 {@link DeviceCredentials}
     */
    @RequestLine("POST /api/device/credentials/generate-dynamic-token?deviceId={deviceId}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DeviceCredentials> generateDynamicToken(@Param("deviceId") String deviceId);
}
