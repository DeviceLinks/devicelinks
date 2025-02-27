package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.DeviceService;
import cn.devicelinks.console.web.converter.DeviceConverter;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceRequest;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.SysUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceController {

    private DeviceService deviceService;

    /**
     * 获取设备列表，支持分页和搜索功能
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     * @return 返回 {@link ApiResponse} 包含设备列表和分页信息，或 {@link ApiResponse} 包含错误信息。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @PostMapping(value = "/filter")
    public ApiResponse getDeviceListByPageable(@Valid PaginationQuery paginationQuery,
                                               @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(this.deviceService.selectByPageable(paginationQuery, searchFieldQuery));
    }

    /**
     * 添加新设备
     *
     * @param request 添加设备请求 {@link AddDeviceRequest}
     * @return 返回 {@link ApiResponse} 包含操作状态码和消息。成功时无额外数据返回。
     * @throws ApiException 如果在处理请求时发生错误，例如参数验证失败。
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Device,
            objectId = "{#executionSucceed ? #result.data.id : #p0.deviceCode}",
            msg = "{#executionSucceed ? '设备添加成功' : '设备添加失败'}",
            activateData = "{#p0}")
    public ApiResponse addDevice(@Valid @RequestBody AddDeviceRequest request) throws ApiException {
        Device device = DeviceConverter.INSTANCE.fromAddDeviceRequest(request);
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        device.setCreateBy(currentUser.getId());
        DeviceAuthenticationMethod authenticationMethod = DeviceAuthenticationMethod.valueOf(request.getAuthenticationMethod());
        device = this.deviceService.addDevice(device, authenticationMethod, request.getAuthenticationAddition());
        return ApiResponse.success(device);
    }
}
