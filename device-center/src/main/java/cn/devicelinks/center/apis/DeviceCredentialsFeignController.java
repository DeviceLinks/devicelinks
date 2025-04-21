package cn.devicelinks.center.apis;

import cn.devicelinks.api.device.center.DeviceCredentialsFeignClient;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.center.configuration.DeviceCenterProperties;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
import cn.devicelinks.framework.common.utils.SecureRandomUtils;
import cn.devicelinks.service.device.DeviceCredentialsService;
import cn.devicelinks.service.device.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 设备凭证Feign客户端接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device/credentials")
@AllArgsConstructor
public class DeviceCredentialsFeignController implements DeviceCredentialsFeignClient {

    private DeviceService deviceService;

    private DeviceCredentialsService deviceCredentialsService;

    private DeviceCenterProperties deviceCenterProperties;

    @Override
    @GetMapping
    public ApiResponse<DeviceCredentials> selectByToken(@RequestParam("token") String token) {
        DeviceCredentials deviceCredentials = deviceCredentialsService.selectByToken(token);
        return ApiResponse.success(deviceCredentials);
    }

    @Override
    @PostMapping("generate-dynamic-token")
    public ApiResponse<DeviceCredentials> generateDynamicToken(@RequestParam("deviceId") String deviceId) {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        DeviceCenterProperties.TokenSetting tokenSetting = deviceCenterProperties.getTokenSetting();
        String dynamicToken = SecureRandomUtils.generateRandomHex(tokenSetting.getIssuedDynamicTokenLength());
        LocalDateTime tokenExpirationTime = LocalDateTime.now().plusSeconds(tokenSetting.getValiditySeconds());
        DeviceCredentials deviceCredentials = deviceCredentialsService.addDynamicToken(deviceId, dynamicToken, tokenExpirationTime);
        return ApiResponse.success(deviceCredentials);
    }
}
