package cn.devicelinks.console.controller;

import cn.devicelinks.service.device.DeviceCredentialsService;
import cn.devicelinks.service.device.DeviceService;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.request.UpdateDeviceCredentialsRequest;
import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
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
public class DeviceCredentialsController {

    private DeviceCredentialsService deviceCredentialsService;

    private DeviceService deviceService;

    /**
     * 获取设备凭证
     *
     * @param deviceId 设备ID {@link DeviceCredentials#getDeviceId()}
     * @return {@link DeviceCredentials}
     * @throws ApiException 遇到的业务逻辑认证异常以及参数认证异常
     */
    @GetMapping(value = "/{deviceId}/credentials")
    public ApiResponse<DeviceCredentials> getDeviceCredentials(@PathVariable("deviceId") String deviceId) throws ApiException {
        return ApiResponse.success(this.deviceCredentialsService.selectByDeviceId(deviceId));
    }

    /**
     * 更新设备凭证
     *
     * @param deviceId 设备ID {@link DeviceCredentials#getDeviceId()}
     * @param request  {@link UpdateDeviceCredentialsRequest}更新设备凭证的请求实体
     * @return {@link DeviceCredentials}
     * @throws ApiException 遇到的业务逻辑认证异常以及参数认证异常
     */
    @PostMapping(value = "/{deviceId}/credentials")
    public ApiResponse<DeviceCredentials> updateDeviceCredentials(@PathVariable("deviceId") String deviceId,
                                                                    @Valid @RequestBody UpdateDeviceCredentialsRequest request) throws ApiException {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        DeviceCredentialsType credentialsType = DeviceCredentialsType.valueOf(request.getCredentialsType());
        DeviceCredentials deviceAuthentication =
                this.deviceCredentialsService.updateCredentials(deviceId, credentialsType, request.getCredentialsAddition());
        return ApiResponse.success(deviceAuthentication);
    }
}
