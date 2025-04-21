package cn.devicelinks.framework.common.feign;

import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.pojos.Device;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import static cn.devicelinks.framework.common.feign.FeignConstants.JSON_CONTENT_TYPE_HEADER;

/**
 * 设备中心设备相关接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceCenterDeviceFeignApi {
    /**
     * 获取指定名称的设备
     *
     * @param deviceName 设备名称 {@link Device#getDeviceName()}
     * @return 设备信息 {@link Device}
     */
    @RequestLine("GET /api/devices?deviceName={deviceName}")
    @Headers(JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Device> getDeviceByName(@Param("deviceName") String deviceName);

    /**
     * 获取指定ID的设备
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 设备信息 {@link Device}
     */
    @RequestLine("GET /api/devices/{deviceId}")
    @Headers(JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Device> getDeviceById(@Param("deviceId") String deviceId);

    /**
     * 解密设备密钥
     *
     * @param deviceId 设备ID
     * @return 解密后的DeviceSecret
     */
    @RequestLine("GET /api/devices/{deviceId}/decrypt-secret")
    @Headers(JSON_CONTENT_TYPE_HEADER)
    ApiResponse<String> decryptDeviceSecret(@Param("deviceId") String deviceId);
}
