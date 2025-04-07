package cn.devicelinks.core.apis;

import cn.devicelinks.core.service.DeviceService;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.feign.CoreServiceDeviceApi;
import cn.devicelinks.framework.common.pojos.Device;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备Api接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/devices")
@AllArgsConstructor
public class DeviceApiController implements CoreServiceDeviceApi {

    private DeviceService deviceService;

    @Override
    @GetMapping
    public ApiResponse<Device> getDeviceByName(String deviceName) {
        Device device = deviceService.getDeviceByName(deviceName);
        return ApiResponse.success(device);
    }

    @Override
    @GetMapping("/{deviceId}")
    public ApiResponse<Device> getDeviceById(@PathVariable("deviceId") String deviceId) {
        Device device = deviceService.selectById(deviceId);
        return ApiResponse.success(device);
    }
}
