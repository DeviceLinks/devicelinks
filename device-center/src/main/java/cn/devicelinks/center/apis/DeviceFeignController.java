package cn.devicelinks.center.apis;

import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.api.device.center.model.request.DynamicRegistrationRequest;
import cn.devicelinks.api.device.center.model.response.DynamicRegistrationResponse;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.center.configuration.DeviceCenterProperties;
import cn.devicelinks.common.secret.AesProperties;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.StatusCode;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceSecret;
import cn.devicelinks.service.device.DeviceSecretService;
import cn.devicelinks.service.device.DeviceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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
public class DeviceFeignController implements DeviceFeignClient {

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
        AesProperties aesProperties = deviceSecret.getEncryptedSecretAddition().getAes();
        if (ObjectUtils.isEmpty(aesProperties.getIv()) || ObjectUtils.isEmpty(aesProperties.getKeyId())) {
            throw new ApiException(StatusCodeConstants.AES_DECRYPTION_ERROR);
        }
        String decryptedSecret = deviceSecretService.decryptSecret(deviceSecret.getEncryptedSecret(), aesProperties.getIv(),
                aesProperties.getKeyId(), deviceCenterProperties.getDeviceSecretKeySet());
        return ApiResponse.success(decryptedSecret);
    }

    @Override
    @PostMapping("/{deviceId}/activate")
    public ApiResponse<Boolean> activateDevice(@PathVariable("deviceId") String deviceId) {
        try {
            this.deviceService.activateDevice(deviceId);
        } catch (Exception e) {
            return ApiResponse.success(Boolean.FALSE);
        }
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    @PostMapping("/dynamic-registration")
    public ApiResponse<DynamicRegistrationResponse> dynamicRegistration(@Valid @RequestBody DynamicRegistrationRequest request) {
        return ApiResponse.success(this.deviceService.dynamicRegistration(request, deviceCenterProperties.getDeviceSecretKeySet()));
    }
}
