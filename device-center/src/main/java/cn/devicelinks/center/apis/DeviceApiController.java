package cn.devicelinks.center.apis;

import cn.devicelinks.center.configuration.DeviceCenterProperties;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.feign.DeviceCenterDeviceFeignApi;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.service.device.DeviceSecretService;
import cn.devicelinks.service.device.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 设备Api接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/devices")
@AllArgsConstructor
public class DeviceApiController implements DeviceCenterDeviceFeignApi {

    private static final StatusCode DEVICE_SECRET_INVALID = StatusCode.build("DEVICE_SECRET_INVALID", "无效的设备密钥.");

    private DeviceService deviceService;

    private DeviceSecretService deviceSecretService;

    private DeviceCenterProperties deviceCenterProperties;

    @Override
    @GetMapping
    public ApiResponse<Device> getDeviceByName(String deviceName) {
        Device device = deviceService.selectByName(deviceName);
        return ApiResponse.success(device);
    }

    @Override
    @GetMapping("/{deviceId}")
    public ApiResponse<Device> getDeviceById(@PathVariable("deviceId") String deviceId) {
        Device device = deviceService.selectById(deviceId);
        return ApiResponse.success(device);
    }

    @Override
    @GetMapping("/{deviceId}/decrypt-secret")
    public ApiResponse<String> decryptDeviceSecret(@PathVariable("deviceId") String deviceId) {
        DeviceSecret deviceSecret = deviceSecretService.getDeviceSecret(deviceId);
        if (deviceSecret == null || (deviceSecret.getExpiresTime() != null && deviceSecret.getExpiresTime().isAfter(LocalDateTime.now()))) {
            throw new ApiException(DEVICE_SECRET_INVALID);
        }
        String decryptedSecret = deviceSecretService.decryptSecret(deviceSecret.getEncryptedSecret(), deviceSecret.getIv(),
                deviceSecret.getSecretKeyVersion(), deviceCenterProperties.getDeviceSecretKeySet());
        return ApiResponse.success(decryptedSecret);
    }
}
