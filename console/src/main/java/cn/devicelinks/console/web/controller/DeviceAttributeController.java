package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceAttributeService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.ExtractUnknownDeviceAttributeRequest;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAttribute;
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

    private DeviceAttributeService deviceAttributeService;

    /**
     * 查询设备属性的最新值
     *
     * @param deviceId            设备ID {@link Device#getId()}
     * @param moduleId            功能模块ID {@link Attribute#getModuleId()}
     * @param attributeName       属性名称 {@link Attribute#getName()}
     * @param attributeIdentifier 属性标识符 {@link Attribute#getIdentifier()}
     * @return 设备属性最新值列表
     * @throws ApiException 查询过程中遇到的业务逻辑异常
     */
    @GetMapping(value = "/{deviceId}/module/{moduleId}/attribute/latest")
    public ApiResponse getDeviceLatestAttribute(@PathVariable("deviceId") String deviceId,
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
    @PostMapping(value = "/attribute/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceAttribute)
    public ApiResponse getReportedAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                                      @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(deviceAttributeService.getByPageable(searchFieldQuery, paginationQuery));
    }

    /**
     * 将未知的属性提取成已知属性
     *
     * @param deviceAttributeId 属性ID {@link DeviceAttribute#getId()}
     * @param request           提取属性请求参数 {@link ExtractUnknownDeviceAttributeRequest}
     * @return 提取后的属性 {@link cn.devicelinks.framework.common.pojos.Attribute}
     * @throws ApiException 遇到的业务逻辑异常
     */
    @PostMapping(value = "/attribute/{deviceAttributeId}/extract")
    @OperationLog(action = LogAction.Extract,
            objectType = LogObjectType.DeviceAttribute,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '提取未知属性成功' : '提取未知属性失败'}",
            activateData = "{#p1}")
    public ApiResponse extractUnknownReportedAttribute(@PathVariable("deviceAttributeId") String deviceAttributeId,
                                                       @Valid @RequestBody ExtractUnknownDeviceAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.deviceAttributeService.extractUnknownAttribute(deviceAttributeId, request));
    }
}
