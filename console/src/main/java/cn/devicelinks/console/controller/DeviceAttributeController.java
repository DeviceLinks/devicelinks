package cn.devicelinks.console.controller;

import cn.devicelinks.api.model.dto.DeviceAttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.AddDeviceAttributeRequest;
import cn.devicelinks.api.model.request.ExtractUnknownDeviceAttributeRequest;
import cn.devicelinks.api.model.request.UpdateDeviceAttributeRequest;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.service.device.DeviceAttributeService;
import cn.devicelinks.service.device.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备属性接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/devices")
@RequiredArgsConstructor
public class DeviceAttributeController {
    private final DeviceService deviceService;
    private final DeviceAttributeService deviceAttributeService;

    /**
     * 查询设备功能模块下所有属性的最新值
     *
     * @param deviceId            设备ID {@link Device#getId()}
     * @param moduleId            功能模块ID {@link Attribute#getModuleId()}
     * @param attributeName       属性名称 {@link Attribute#getName()}
     * @param attributeIdentifier 属性标识符 {@link Attribute#getIdentifier()}
     * @return 设备属性最新值列表
     * @throws ApiException 查询过程中遇到的业务逻辑异常
     */
    @GetMapping(value = "/{deviceId}/modules/{moduleId}/attributes/latest")
    public ApiResponse<List<DeviceAttributeLatestDTO>> getDeviceLatestAttribute(@PathVariable("deviceId") String deviceId,
                                                                                @PathVariable("moduleId") String moduleId,
                                                                                @RequestParam(value = "attributeName", required = false) String attributeName,
                                                                                @RequestParam(value = "attributeIdentifier", required = false) String attributeIdentifier) throws ApiException {
        return ApiResponse.success(this.deviceAttributeService.getLatestAttribute(deviceId, moduleId, attributeName, attributeIdentifier));
    }

    /**
     * 分页查询设备的属性列表
     *
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @param searchFieldQuery 检索字段对象 {@link SearchFieldQuery}
     * @return 设备的属性列表
     * @throws ApiException 查询过程中遇到的业务异常
     */
    @PostMapping(value = "/attributes/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceAttribute)
    public ApiResponse<PageResult<DeviceAttributeDTO>> getReportedAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                                                                      @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(deviceAttributeService.getByPageable(searchFieldQuery, paginationQuery));
    }

    /**
     * 将未知的属性提取成已知属性
     *
     * @param deviceAttributeId 属性ID {@link DeviceAttribute#getId()}
     * @param request           提取属性请求参数 {@link ExtractUnknownDeviceAttributeRequest}
     * @return 提取后的属性 {@link Attribute}
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/attributes/{deviceAttributeId}/extract")
    @OperationLog(action = LogAction.Extract,
            objectType = LogObjectType.DeviceAttribute,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '提取未知属性成功' : '提取未知属性失败'}",
            activateData = "{#p1}")
    public ApiResponse<Attribute> extractUnknownReportedAttribute(@PathVariable("deviceAttributeId") String deviceAttributeId,
                                                                  @Valid @RequestBody ExtractUnknownDeviceAttributeRequest request) throws ApiException {
        UserAuthorizedAddition authorizedAddition = UserDetailsContext.getUserAddition();
        return ApiResponse.success(this.deviceAttributeService.extractUnknownAttribute(deviceAttributeId, request, authorizedAddition));
    }

    /**
     * 添加设备属性
     *
     * @param request 添加设备属性请求参数 {@link AddDeviceAttributeRequest}
     * @return 新增的属性
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/{deviceId}/attributes")
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.DeviceAttribute,
            objectId = "{#executionSucceed ? #result.data.id : #p1.identifier}",
            msg = "{#executionSucceed ? '添加属性成功' : '添加属性失败'}",
            activateData = "{#p1}")
    public ApiResponse<DeviceAttribute> addDeviceAttribute(@PathVariable("deviceId") String deviceId,
                                                           @Valid @RequestBody AddDeviceAttributeRequest request) throws ApiException {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        return ApiResponse.success(this.deviceAttributeService.addDeviceAttribute(device, request));
    }

    /**
     * 更新设备属性
     *
     * @param deviceAttributeId 设备属性ID {@link DeviceAttribute#getId()}
     * @param request           更新设备属性请求参数 {@link UpdateDeviceAttributeRequest}
     * @return 更新后的设备属性
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/{deviceId}/attributes/{attributeId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceAttribute,
            objectId = "{#executionSucceed ? #result.data.id : #p1}",
            object = "{@deviceAttributeServiceImpl.selectById(#p1)}",
            msg = "{#executionSucceed ? '更新属性值成功' : '更新属性值失败'}",
            activateData = "{#p2}")
    public ApiResponse<DeviceAttribute> updateDeviceAttribute(@PathVariable("deviceId") String deviceId,
                                                              @PathVariable("attributeId") String deviceAttributeId,
                                                              @Valid @RequestBody UpdateDeviceAttributeRequest request) throws ApiException {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        return ApiResponse.success(this.deviceAttributeService.updateDeviceAttribute(deviceAttributeId, request));
    }
}
