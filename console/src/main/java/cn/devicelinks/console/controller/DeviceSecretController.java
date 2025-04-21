package cn.devicelinks.console.controller;

import cn.devicelinks.api.device.center.CommonFeignClient;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.converter.DeviceSecretConverter;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.api.ApiResponseUnwrapper;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.exception.DeviceLinksException;
import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.framework.common.secret.AesSecretKeySet;
import cn.devicelinks.framework.jdbc.model.dto.DeviceSecretDTO;
import cn.devicelinks.service.device.DeviceSecretService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 设备密钥接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceSecretController {

    private DeviceSecretService deviceSecretService;

    private CommonFeignClient commonFeignClient;

    /**
     * 查询设备密钥
     *
     * @param deviceId 设备ID {@link DeviceSecret#getDeviceId()}
     * @return 设备密钥传输实体 {@link DeviceSecretDTO}
     * @throws ApiException 查询过程中遇到的异常
     */
    @GetMapping(value = "/{deviceId}/secret")
    public ApiResponse<DeviceSecretDTO> getDeviceSecret(@PathVariable("deviceId") String deviceId) throws ApiException {
        AesSecretKeySet deviceSecretKeySet = ApiResponseUnwrapper.unwrap(commonFeignClient.getAesSecretKeys());
        DeviceSecret deviceSecret = deviceSecretService.getDeviceSecret(deviceId);
        if (deviceSecret == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_HAVE_SECRET);
        }
        DeviceSecretDTO deviceSecretDTO = DeviceSecretConverter.INSTANCE.fromDeviceSecret(deviceSecret);
        try {
            String secret = deviceSecretService.decryptSecret(deviceSecret.getEncryptedSecret(), deviceSecret.getIv(),
                    deviceSecret.getSecretKeyVersion(), deviceSecretKeySet);
            deviceSecretDTO.setSecret(secret);
        } catch (DeviceLinksException e) {
            throw new ApiException(StatusCodeConstants.DEVICE_SECRET_DECRYPTION_ERROR);
        }
        return ApiResponse.success(deviceSecretDTO);
    }

    /**
     * 重新生成设备密钥
     *
     * @param deviceId 设备ID {@link DeviceSecret#getDeviceId()}
     * @return 设备密钥传输实体 {@link DeviceSecretDTO}
     * @throws ApiException 重新生成过程中遇到的异常
     */
    @PostMapping(value = "/{deviceId}/secret-regenerate")
    public ApiResponse<DeviceSecretDTO> regenerateDeviceSecret(@PathVariable("deviceId") String deviceId) throws ApiException {
        AesSecretKeySet deviceSecretKeySet = ApiResponseUnwrapper.unwrap(commonFeignClient.getAesSecretKeys());
        DeviceSecretDTO deviceSecretDTO = deviceSecretService.regenerate(deviceId, deviceSecretKeySet);
        return ApiResponse.success(deviceSecretDTO);
    }
}
