package cn.devicelinks.center.apis;

import cn.devicelinks.api.device.center.CommonFeignClient;
import cn.devicelinks.center.configuration.DeviceCenterProperties;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.common.secret.AesSecretKeySet;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用公共Feign客户端实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping("/api/common")
@AllArgsConstructor
public class CommonFeignController implements CommonFeignClient {

    private DeviceCenterProperties deviceCenterProperties;

    @Override
    @GetMapping("/aes-secret-keys")
    public ApiResponse<AesSecretKeySet> getAesSecretKeys() {
        return ApiResponse.success(deviceCenterProperties.getDeviceSecretKeySet());
    }
}
