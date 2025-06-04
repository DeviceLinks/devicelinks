package cn.devicelinks.api.device.center;

import cn.devicelinks.api.device.center.model.response.DecryptTokenResponse;
import cn.devicelinks.api.device.center.model.response.GenerateDynamicTokenResponse;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.openfeign.cache.FeignCacheable;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceCredentials;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 设备凭证服务的 OpenFeign 客户端接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface DeviceCredentialsFeignClient {
    /**
     * 根据哈希后的令牌值查询设备凭证信息
     *
     * @param tokenHash 设备凭证的哈希值（非明文）
     * @return 设备凭证信息 {@link DeviceCredentials}
     * @see DeviceCredentialsType#DynamicToken
     * @see DeviceCredentialsType#StaticToken
     */
    @RequestLine("GET /api/device/credentials?tokenHash={tokenHash}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    @FeignCacheable
    ApiResponse<DeviceCredentials> selectByTokenHash(@Param("tokenHash") String tokenHash);

    /**
     * 解密令牌哈希值，获取原始动态令牌内容
     *
     * @param tokenHash 设备凭证的哈希值（非明文）
     * @return 解密结果，包含原始令牌信息
     */
    @RequestLine("GET /api/device/credentials/token-decrypt?tokenHash={tokenHash}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    @FeignCacheable
    ApiResponse<DecryptTokenResponse> decryptToken(@Param("tokenHash") String tokenHash);

    /**
     * 为指定设备生成动态令牌凭据
     *
     * @param deviceId 设备 ID {@link Device#getId()}
     * @return 动态令牌信息 {@link GenerateDynamicTokenResponse}
     */
    @RequestLine("POST /api/device/credentials/generate-dynamic-token?deviceId={deviceId}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<GenerateDynamicTokenResponse> generateDynamicToken(@Param("deviceId") String deviceId);
}
