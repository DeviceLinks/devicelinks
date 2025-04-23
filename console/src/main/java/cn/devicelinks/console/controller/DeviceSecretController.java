package cn.devicelinks.console.controller;

import cn.devicelinks.api.device.center.CommonFeignClient;
import cn.devicelinks.api.model.converter.DeviceSecretConverter;
import cn.devicelinks.api.model.dto.DeviceSecretDTO;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.exception.DeviceLinksException;
import cn.devicelinks.common.secret.AesProperties;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.entity.DeviceSecret;
import cn.devicelinks.service.device.DeviceSecretService;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;
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
            AesProperties aesProperties = deviceSecret.getEncryptedSecretAddition().getAes();
            if (ObjectUtils.isEmpty(aesProperties.getIv()) || ObjectUtils.isEmpty(aesProperties.getKeyVersion())) {
                throw new ApiException(StatusCodeConstants.AES_DECRYPTION_ERROR);
            }
            String secret = deviceSecretService.decryptSecret(deviceSecret.getEncryptedSecret(), aesProperties.getIv(),
                    aesProperties.getKeyVersion(), deviceSecretKeySet);
            deviceSecretDTO.setSecret(secret);
        } catch (DeviceLinksException e) {
            throw new ApiException(StatusCodeConstants.AES_DECRYPTION_ERROR);
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
