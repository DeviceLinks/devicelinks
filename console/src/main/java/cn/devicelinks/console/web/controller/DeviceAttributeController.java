package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceAttributeDesiredService;
import cn.devicelinks.console.service.DeviceAttributeReportedService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceDesiredAttributeRequest;
import cn.devicelinks.console.web.request.UpdateDeviceDesiredAttributeRequest;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 设备属性接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceAttributeController {

    private DeviceAttributeReportedService reportedAttributeService;

    private DeviceAttributeDesiredService desiredAttributeService;

    /**
     * 分页查询设备上报的属性列表
     *
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @param searchFieldQuery 检索字段对象 {@link SearchFieldQuery}
     * @return 设备上报的属性列表
     * @throws ApiException 查询过程中遇到的业务异常
     */
    @PostMapping(value = "/reported/attribute/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceReportedAttribute)
    public ApiResponse getReportedAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                                      @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(reportedAttributeService.getByPageable(searchFieldQuery, paginationQuery));
    }

    /**
     * 分页查询设备期望属性列表
     *
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @param searchFieldQuery 检索字段对象 {@link SearchFieldQuery}
     * @return 设备期望属性列表
     * @throws ApiException 查询过程中遇到的业务异常
     */
    @PostMapping(value = "/desired/attribute/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceDesiredAttribute)
    public ApiResponse getDesiredAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                                     @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(desiredAttributeService.getByPageable(searchFieldQuery, paginationQuery));
    }

    /**
     * 添加设备期望属性
     *
     * @param request 添加设备期望属性请求参数 {@link AddDeviceDesiredAttributeRequest}
     * @return 新增的期望属性
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/{deviceId}/module/{moduleId}/desired/attribute")
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.DeviceDesiredAttribute,
            objectId = "{#executionSucceed ? #result.data.id : #p2.identifier}",
            msg = "{#executionSucceed ? '添加设备期望属性成功' : '添加设备期望属性失败'}",
            activateData = "{#p2}")
    public ApiResponse addDesiredAttribute(@PathVariable("deviceId") String deviceId,
                                           @PathVariable("moduleId") String moduleId,
                                           @Valid @RequestBody AddDeviceDesiredAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.desiredAttributeService.addDesiredAttribute(deviceId, moduleId, request));
    }

    /**
     * 更新设备期望属性
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getId()}
     * @param request            更新设备期望属性请求参数 {@link UpdateDeviceDesiredAttributeRequest}
     * @return 更新后的设备期望属性
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/desired/attribute/{desiredAttributeId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceDesiredAttribute,
            objectId = "{#executionSucceed ? #result.data.id : #p0}",
            object = "{@deviceAttributeDesiredServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '更新设备期望属性成功' : '更新设备期望属性失败'}",
            activateData = "{#p1}")
    public ApiResponse updateDesiredAttribute(@PathVariable("desiredAttributeId") String desiredAttributeId,
                                              @Valid @RequestBody UpdateDeviceDesiredAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.desiredAttributeService.updateDesiredAttribute(desiredAttributeId, request));
    }
}
