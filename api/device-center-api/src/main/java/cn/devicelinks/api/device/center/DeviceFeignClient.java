package cn.devicelinks.api.device.center;

import cn.devicelinks.api.device.center.response.EncryptDeviceSecretResponse;
import cn.devicelinks.api.support.feign.FeignConstants;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceSecret;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 设备Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceFeignClient {
    /**
     * 根据设备名称获取设备信息
     *
     * @param deviceName 设备名称，对应 {@link Device#getDeviceName()}
     * @return 包含设备信息的 ApiResponse 对象，设备信息类型为 {@link Device}
     */
    @RequestLine("GET /api/devices?deviceName={deviceName}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Device> getDeviceByName(@Param("deviceName") String deviceName);

    /**
     * 根据设备ID获取设备信息
     *
     * @param deviceId 设备ID，对应 {@link Device#getId()}
     * @return 包含设备信息的 ApiResponse 对象，设备信息类型为 {@link Device}
     */
    @RequestLine("GET /api/devices/{deviceId}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Device> getDeviceById(@Param("deviceId") String deviceId);

    /**
     * 解密指定设备ID的设备密钥
     *
     * @param deviceId 设备ID
     * @return 包含解密后设备密钥的 ApiResponse 对象，密钥类型为 String
     */
    @RequestLine("GET /api/devices/{deviceId}/decrypt-secret")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<String> decryptDeviceSecret(@Param("deviceId") String deviceId);

    /**
     * 加密指定设备的明文密钥
     *
     * @param deviceId 设备ID {@link DeviceSecret#getDeviceId()}
     * @param secret   未加密的设备密钥
     * @return 加密后的设备密钥 {@link DeviceSecret#getEncryptedSecret()}
     */
    @RequestLine("POST /api/devices/{deviceId}/encrypt-secret?secret={secret}")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<EncryptDeviceSecretResponse> encryptDeviceSecret(@Param("deviceId") String deviceId, @Param("secret") String secret);

    /**
     * 激活设备
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 是否激活成功，激活成功时返回true
     */
    @RequestLine("POST /api/devices/{deviceId}/activate")
    @Headers(FeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Boolean> activateDevice(@Param("deviceId") String deviceId);
}
