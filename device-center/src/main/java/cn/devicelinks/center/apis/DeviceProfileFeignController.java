package cn.devicelinks.center.apis;

import cn.devicelinks.api.device.center.DeviceProfileFeignClient;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.service.device.DeviceProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备配置文件Feign客户端接口实现
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device-profiles")
@RequiredArgsConstructor
public class DeviceProfileFeignController implements DeviceProfileFeignClient {

    private final DeviceProfileService deviceProfileService;

    @Override
    @GetMapping
    public ApiResponse<DeviceProfile> getByProvisionKey(String provisionKey) {
        deviceProfileService.getByProvisionKey(provisionKey);
        DeviceProfile deviceProfile = deviceProfileService.getByProvisionKey(provisionKey);
        return ApiResponse.success(deviceProfile);
    }
}
