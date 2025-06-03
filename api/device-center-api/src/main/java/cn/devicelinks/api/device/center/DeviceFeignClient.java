package cn.devicelinks.api.device.center;

import cn.devicelinks.api.device.center.model.request.DynamicRegistrationRequest;
import cn.devicelinks.api.device.center.model.response.DynamicRegistrationResponse;
import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.openfeign.cache.FeignCacheable;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.Device;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 设备Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface DeviceFeignClient {
    /**
     * 根据设备名称获取设备信息
     *
     * @param deviceName 设备名称，对应 {@link Device#getDeviceName()}
     * @return 包含设备信息的 ApiResponse 对象，设备信息类型为 {@link Device}
     */
    @RequestLine("GET /api/devices?deviceName={deviceName}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    @FeignCacheable
    ApiResponse<Device> getDeviceByName(@Param("deviceName") String deviceName);

    /**
     * 根据设备ID获取设备信息
     *
     * @param deviceId 设备ID，对应 {@link Device#getId()}
     * @return 包含设备信息的 ApiResponse 对象，设备信息类型为 {@link Device}
     */
    @RequestLine("GET /api/devices/{deviceId}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    @FeignCacheable
    ApiResponse<Device> getDeviceById(@Param("deviceId") String deviceId);

    /**
     * 解密指定设备ID的设备密钥
     *
     * @param deviceId 设备ID
     * @return 包含解密后设备密钥的 ApiResponse 对象，密钥类型为 String
     */
    @RequestLine("GET /api/devices/{deviceId}/decrypt-secret")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    @FeignCacheable
    ApiResponse<String> decryptDeviceSecret(@Param("deviceId") String deviceId);

    /**
     * 激活设备
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 是否激活成功，激活成功时返回true
     */
    @RequestLine("POST /api/devices/{deviceId}/activate")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Boolean> activateDevice(@Param("deviceId") String deviceId);

    /**
     * 动态注册
     *
     * @param request 动态注册请求对象
     * @return {@link DynamicRegistrationResponse}
     */
    @RequestLine("POST /api/devices/dynamic-registration")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DynamicRegistrationResponse> dynamicRegistration(DynamicRegistrationRequest request);
}
