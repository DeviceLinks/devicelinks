package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceShadowService;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.DeviceShadow;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备影子接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceShadowController {

    private DeviceShadowService deviceShadowService;

    /**
     * 获取设备影子
     *
     * @param deviceId 设备ID {@link DeviceShadow#getDeviceId()}
     * @return 设备影子 {@link DeviceShadow}
     * @throws ApiException 查询时遇到的异常
     */
    @GetMapping(value = "/{deviceId}/shadow")
    public ApiResponse getDeviceShadow(@PathVariable("deviceId") String deviceId) throws ApiException {
        return ApiResponse.success(deviceShadowService.selectByDeviceId(deviceId));
    }
}
