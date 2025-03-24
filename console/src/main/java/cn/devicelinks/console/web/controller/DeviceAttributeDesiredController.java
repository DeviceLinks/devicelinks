package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceAttributeDesiredService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceDesiredAttributeRequest;
import cn.devicelinks.console.web.request.ExtractUnknownDesiredAttributeRequest;
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
 * 设备期望属性控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device")
@AllArgsConstructor
public class DeviceAttributeDesiredController {

    private DeviceAttributeDesiredService desiredAttributeService;
   
    /**
     * 分页查询设备期望属性列表
     *
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @param searchFieldQuery 检索字段对象 {@link SearchFieldQuery}
     * @return 设备期望属性列表
     * @throws ApiException 查询过程中遇到的业务异常
     */
    @PostMapping(value = "/attribute/desired/filter")
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
    @PostMapping(value = "/{deviceId}/module/{moduleId}/attribute/desired")
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.DeviceDesiredAttribute,
            objectId = "{#executionSucceed ? #result.data.id : #p2.identifier}",
            msg = "{#executionSucceed ? '添加期望属性成功' : '添加期望属性失败'}",
            activateData = "{#p2}")
    public ApiResponse addDesiredAttribute(@PathVariable("deviceId") String deviceId,
                                           @PathVariable("moduleId") String moduleId,
                                           @Valid @RequestBody AddDeviceDesiredAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.desiredAttributeService.addDesiredAttribute(deviceId, moduleId, request));
    }

    /**
     * 更新设备期望属性值
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getId()}
     * @param request            更新设备期望属性请求参数 {@link UpdateDeviceDesiredAttributeRequest}
     * @return 更新后的设备期望属性
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/attribute/{desiredAttributeId}/desired")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceDesiredAttribute,
            objectId = "{#executionSucceed ? #result.data.id : #p0}",
            object = "{@deviceAttributeDesiredServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '更新期望属性值成功' : '更新期望属性值失败'}",
            activateData = "{#p1}")
    public ApiResponse updateDesiredAttribute(@PathVariable("desiredAttributeId") String desiredAttributeId,
                                              @Valid @RequestBody UpdateDeviceDesiredAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.desiredAttributeService.updateDesiredAttribute(desiredAttributeId, request));
    }

    /**
     * 将未知的期望属性提取成已知属性
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getAttributeId()}
     * @param request            提取期望属性请求参数 {@link ExtractUnknownDesiredAttributeRequest}
     * @return 提取后的属性 {@link cn.devicelinks.framework.common.pojos.Attribute}
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/attribute/{desiredAttributeId}/desired/extract")
    @OperationLog(action = LogAction.Extract,
            objectType = LogObjectType.DeviceDesiredAttribute,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '提取未知期望属性成功' : '提取未知期望属性失败'}",
            activateData = "{#p1}")
    public ApiResponse extractUnknownDesiredAttribute(@PathVariable("desiredAttributeId") String desiredAttributeId,
                                                      @Valid @RequestBody ExtractUnknownDesiredAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.desiredAttributeService.extractUnknownAttribute(desiredAttributeId, request));
    }

    /**
     * 删除期望属性
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getId()}
     * @return 已删除的期望属性 {@link DeviceAttributeDesired}
     * @throws ApiException 遇到的业务逻辑异常
     */
    @DeleteMapping(value = "/attribute/{desiredAttributeId}/desired")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.DeviceDesiredAttribute,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '删除期望属性成功' : '删除期望属性失败'}",
            activateData = "{#p0}")
    public ApiResponse deleteDesiredAttribute(@PathVariable("desiredAttributeId") String desiredAttributeId) throws ApiException {
        return ApiResponse.success(this.desiredAttributeService.deleteDesiredAttribute(desiredAttributeId));
    }
}
