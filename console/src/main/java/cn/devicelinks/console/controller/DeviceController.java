package cn.devicelinks.console.controller;

import cn.devicelinks.api.device.center.CommonFeignClient;
import cn.devicelinks.api.model.converter.DeviceConverter;
import cn.devicelinks.api.model.dto.DeviceDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.AddDeviceRequest;
import cn.devicelinks.api.model.request.BatchDeleteDeviceRequest;
import cn.devicelinks.api.model.request.BatchUpdateDeviceEnabledRequest;
import cn.devicelinks.api.model.request.UpdateDeviceRequest;
import cn.devicelinks.api.model.response.BatchDeleteDeviceResponse;
import cn.devicelinks.api.model.response.BatchUpdateDeviceEnabledResponse;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.common.UpdateDeviceEnabledAction;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.FunctionModule;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.service.device.DeviceService;
import cn.devicelinks.service.product.FunctionModuleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    private CommonFeignClient commonFeignClient;

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
            objectId = "{#executionSucceed ? #result.data.id : #p0.deviceName}",
            msg = "{#executionSucceed ? '设备添加成功' : '设备添加失败'}",
            activateData = "{#p0}")
    public ApiResponse<Device> addDevice(@Valid @RequestBody AddDeviceRequest request) throws ApiException {
        Device device = DeviceConverter.INSTANCE.fromAddDeviceRequest(request);
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        device.setCreateBy(currentUser.getId());
        DeviceCredentialsType credentialsType = DeviceCredentialsType.valueOf(request.getCredentialsType());
        AesSecretKeySet deviceSecretKeySet = ApiResponseUnwrapper.unwrap(commonFeignClient.getAesSecretKeys());
        device = this.deviceService.addDevice(device, credentialsType, request.getCredentialsAddition(), deviceSecretKeySet);
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

    /**
     * 批量删除设备
     * <p>
     * 如果设备删除时遇到问题，则通过{@link BatchDeleteDeviceResponse}进行返回
     *
     * @param request 批量删除设备的请求实体 {@link BatchDeleteDeviceRequest}
     * @return {@link BatchDeleteDeviceResponse}
     * @throws ApiException 删除过程中遇到的业务逻辑异常
     */
    @DeleteMapping(value = "/batch")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Device,
            msg = "{#executionSucceed ? '设备删除成功' : '设备删除失败'}",
            batch = true)
    public ApiResponse<BatchDeleteDeviceResponse> batchDeleteDevices(@Valid @RequestBody BatchDeleteDeviceRequest request) throws ApiException {
        Map<String, String> failureReasonMap = this.deviceService.batchDeleteDevices(request.getDeviceIds());
        return ApiResponse.success(BatchDeleteDeviceResponse.builder().failureReasonMap(failureReasonMap).build());
    }

    /**
     * 批量更新更新设备启用状态
     *
     * @param request 批量更新设备启用状态的请求实体 {@link BatchUpdateDeviceEnabledRequest}
     * @return {@link BatchUpdateDeviceEnabledResponse}
     * @throws ApiException 更新状态过程中遇到的业务逻辑异常
     */
    @PostMapping(value = "/enabled-batch")
    @OperationLog(action = LogAction.UpdateStatus,
            objectType = LogObjectType.Device,
            msg = "{#executionSucceed ? '设备启用状态更新成功' : '设备启用状态更新失败'}",
            batch = true)
    public ApiResponse<BatchUpdateDeviceEnabledResponse> batchUpdateDeviceStatus(@Valid @RequestBody BatchUpdateDeviceEnabledRequest request) throws ApiException {
        UpdateDeviceEnabledAction updateEnabledAction = UpdateDeviceEnabledAction.valueOf(request.getAction());
        Map<String, String> failureReasonMap = this.deviceService.batchUpdateEnabled(updateEnabledAction, request.getDeviceIds());
        return ApiResponse.success(BatchUpdateDeviceEnabledResponse.builder().failureReasonMap(failureReasonMap).build());
    }
}
