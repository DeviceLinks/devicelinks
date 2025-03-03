package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceAuthenticationService;
import cn.devicelinks.console.service.DeviceService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.request.UpdateDeviceAuthorizationRequest;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 设备鉴权接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceAuthenticationController {

    private DeviceAuthenticationService deviceAuthenticationService;

    private DeviceService deviceService;

    /**
     * 获取设备凭证
     *
     * @param deviceId 设备ID {@link DeviceAuthentication#getDeviceId()}
     * @return {@link DeviceAuthentication}
     * @throws ApiException 遇到的业务逻辑认证异常以及参数认证异常
     */
    @GetMapping(value = "/{deviceId}/authorization")
    public ApiResponse getDeviceAuthorization(@PathVariable("deviceId") String deviceId) throws ApiException {
        return ApiResponse.success(this.deviceAuthenticationService.selectByDeviceId(deviceId));
    }

    /**
     * 更新设备凭证
     *
     * @param deviceId 设备ID {@link DeviceAuthentication#getDeviceId()}
     * @param request  {@link UpdateDeviceAuthorizationRequest}更新设备凭证的请求实体
     * @return {@link DeviceAuthentication}
     * @throws ApiException 遇到的业务逻辑认证异常以及参数认证异常
     */
    @PostMapping(value = "/{deviceId}/authorization")
    public ApiResponse updateDeviceAuthorization(@PathVariable("deviceId") String deviceId,
                                                 @Valid @RequestBody UpdateDeviceAuthorizationRequest request) throws ApiException {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        DeviceAuthenticationMethod authenticationMethod = DeviceAuthenticationMethod.valueOf(request.getAuthenticationMethod());
        DeviceAuthentication deviceAuthentication =
                this.deviceAuthenticationService.updateAuthentication(deviceId, authenticationMethod, request.getAuthenticationAddition());
        return ApiResponse.success(deviceAuthentication);
    }
}
