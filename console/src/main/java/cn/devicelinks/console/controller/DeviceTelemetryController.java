package cn.devicelinks.console.controller;

import cn.devicelinks.service.device.DeviceTelemetryService;
import cn.devicelinks.api.support.query.PaginationQuery;
import cn.devicelinks.api.support.query.SearchFieldQuery;
import cn.devicelinks.api.support.request.AddDeviceTelemetryRequest;
import cn.devicelinks.api.support.search.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 遥测数据接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceTelemetryController {

    private DeviceTelemetryService telemetryService;

    /**
     * 获取遥测数据列表，支持分页和搜索功能。
     * <p>
     * 此方法根据提供的分页查询参数和搜索字段查询参数，返回符合条件的遥测数据列表。
     *
     * @param paginationQuery  分页查询参数，包含当前页码和每页大小 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数，包含用于过滤遥测数据的字段 {@link SearchFieldQuery}
     * @return 返回成功响应 {@link ApiResponse}，其中包含符合条件的遥测数据列表。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @PostMapping(value = "/telemetry/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceTelemetry)
    public ApiResponse<PageResult<DeviceTelemetry>> getTelemetryByPageable(@Valid PaginationQuery paginationQuery,
                                              @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<DeviceTelemetry> pageResult = this.telemetryService.getTelemetryByPage(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
    }

    /**
     * 添加设备遥测数据
     *
     * @param deviceId 设备ID {@link DeviceTelemetry#getDeviceId()}
     * @param request  添加遥测数据请求实体 {@link AddDeviceTelemetryRequest}
     * @return 已添加的设备遥测数据实体 {@link DeviceTelemetry}
     * @throws ApiException 处理过程中遇到的业务逻辑异常
     */
    @PostMapping(value = "/{deviceId}/telemetry")
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.DeviceTelemetry,
            objectId = "{#executionSucceed ? #result.data.id : null}",
            msg = "{#executionSucceed ? '添加设备遥测数据成功' : '添加设备遥测数据失败'}",
            activateData = "{#p1}")
    public ApiResponse<DeviceTelemetry> addTelemetry(@PathVariable("deviceId") String deviceId,
                                    @Valid @RequestBody AddDeviceTelemetryRequest request) throws ApiException {
        return ApiResponse.success(this.telemetryService.addTelemetry(deviceId, request));
    }

    /**
     * 删除设备遥测数据
     *
     * @param deviceId    设备ID {@link DeviceTelemetry#getDeviceId()}
     * @param telemetryId 遥测数据ID {@link DeviceTelemetry#getId()}
     * @return 已删除的设备遥测数据实体 {@link DeviceTelemetry}
     * @throws ApiException 处理过程中遇到的业务逻辑异常
     */
    @DeleteMapping(value = "/{deviceId}/telemetry/{telemetryId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.DeviceTelemetry,
            objectId = "{#p1}",
            msg = "{#executionSucceed? '删除设备遥测数据成功' : '删除设备遥测数据失败'}",
            activateData = "{#executionSucceed? #result.data : #p1}")
    public ApiResponse<DeviceTelemetry> deleteTelemetry(@PathVariable("deviceId") String deviceId,
                                       @PathVariable("telemetryId") String telemetryId) throws ApiException {
        return ApiResponse.success(this.telemetryService.deleteTelemetry(deviceId, telemetryId));
    }
}
