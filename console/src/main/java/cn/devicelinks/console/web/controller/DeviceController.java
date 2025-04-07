package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.DeviceService;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.converter.DeviceConverter;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceRequest;
import cn.devicelinks.console.web.request.UpdateDeviceRequest;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private FunctionModuleService functionModuleService;

    /**
     * 获取设备列表，支持分页和搜索功能
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     * @return 返回 {@link ApiResponse} 包含设备列表和分页信息，或 {@link ApiResponse} 包含错误信息。
     * @throws ApiException 抛出处理过程中遇到的异常
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.Device)
    public ApiResponse<PageResult<Device>> getDeviceListByPageable(@Valid PaginationQuery paginationQuery,
                                                                   @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(this.deviceService.selectByPageable(paginationQuery, searchFieldQuery));
    }

    /**
     * 获取设备详情
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 设备详情数据传输实体 {@link DeviceDTO}
     * @throws ApiException 抛出处理过程中遇到的异常
     */
    @GetMapping(value = "/{deviceId}")
    public ApiResponse<DeviceDTO> getDeviceInfo(@PathVariable("deviceId") String deviceId) throws ApiException {
        return ApiResponse.success(this.deviceService.selectByDeviceId(deviceId));
    }

    /**
     * 添加新设备
     *
     * @param request 添加设备请求 {@link AddDeviceRequest}
     * @return 返回 {@link ApiResponse} 包含操作状态码和消息。成功时无额外数据返回。
     * @throws ApiException 抛出处理过程中遇到的异常
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Device,
            objectId = "{#executionSucceed ? #result.data.id : #p0.deviceCode}",
            msg = "{#executionSucceed ? '设备添加成功' : '设备添加失败'}",
            activateData = "{#p0}")
    public ApiResponse<Device> addDevice(@Valid @RequestBody AddDeviceRequest request) throws ApiException {
        Device device = DeviceConverter.INSTANCE.fromAddDeviceRequest(request);
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        device.setCreateBy(currentUser.getId());
        DeviceAuthenticationMethod authenticationMethod = DeviceAuthenticationMethod.valueOf(request.getAuthenticationMethod());
        device = this.deviceService.addDevice(device, authenticationMethod, request.getAuthenticationAddition());
        return ApiResponse.success(device);
    }

    /**
     * 更新设备信息
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @param request  更新设备请求实体 {@link UpdateDeviceRequest}
     * @return 返回更新后的设备信息
     * @throws ApiException 抛出处理过程中遇到的异常
     */
    @PostMapping(value = "/{deviceId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.Device,
            objectId = "{#executionSucceed ? #result.data.id : #p0}",
            object = "{@deviceServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '设备更成功' : '设备更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<Device> updateDevice(@PathVariable("deviceId") String deviceId,
                                    @Valid @RequestBody UpdateDeviceRequest request) throws ApiException {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        // @formatter:off
        device.setNoteName(request.getNoteName())
                .setTags(request.getTags())
                .setMark(request.getMark());
        // @formatter:on
        device = this.deviceService.updateDevice(device);
        return ApiResponse.success(device);
    }

    /**
     * 删除设备信息
     * <p>
     * 仅已禁用的设备允许删除
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 删除后的设备信息
     * @throws ApiException 抛出处理过程中遇到的异常
     */
    @DeleteMapping(value = "/{deviceId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Device,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '设备删除成功' : '设备删除失败'}",
            activateData = "{#executionSucceed ? #result.data : #p0}")
    public ApiResponse<Device> deleteDevice(@PathVariable("deviceId") String deviceId) throws ApiException {
        Device deletedDevice = this.deviceService.deleteDevice(deviceId);
        return ApiResponse.success(deletedDevice);
    }

    /**
     * 启用/禁用设备
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @param enabled  是否启用 {@link Device#isEnabled()}
     * @return {@link ApiResponse}
     * @throws ApiException 抛出处理过程中遇到的异常
     */
    @PostMapping(value = "/{deviceId}/enabled")
    @OperationLog(action = LogAction.UpdateStatus,
            objectType = LogObjectType.Device,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '设备启用状态更新成功' : '设备启用状态更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<Object> updateDeviceStatus(@PathVariable("deviceId") String deviceId,
                                          @NotNull Boolean enabled) throws ApiException {
        this.deviceService.updateEnabled(deviceId, enabled);
        return ApiResponse.success();
    }

    /**
     * 查询设备的功能模块列表
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 设备所属产品下定义的功能模块列表，仅返回有效并未删除的数据
     * @throws ApiException 执行过程中遇到的业务逻辑异常
     */
    @GetMapping(value = "/{deviceId}/function/module")
    public ApiResponse<List<FunctionModule>> getDeviceFunctionModuleList(@PathVariable("deviceId") String deviceId) throws ApiException {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        return ApiResponse.success(this.functionModuleService.getProductFunctionModule(device.getProductId()));
    }
}
