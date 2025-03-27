package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.DeviceTagService;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceTagRequest;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.DeviceTag;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 设备标签接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device-tag")
@AllArgsConstructor
public class DeviceTagController {

    private DeviceTagService deviceTagService;

    /**
     * 查询设备标签列表
     *
     * @param searchFieldQuery 检索字段查询参数
     * @return 设备标签列表
     * @throws ApiException 查询过程中遇到的业务逻辑异常
     */
    @PostMapping("/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceTag)
    public ApiResponse getDeviceTagList(@Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(this.deviceTagService.selectDeviceTagList(searchFieldQuery.toSearchFieldConditionList()));
    }

    /**
     * 新增设备标签
     *
     * @param request 新增设备标签请求参数实体
     * @return 新增的设备标签
     * @throws ApiException 新增时遇到的业务逻辑异常
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.DeviceTag,
            objectId = "{#executionSucceed? #result.data.id : #p0.name}",
            msg = "{#executionSucceed? '设备标签添加成功' : '设备标签添加失败'}",
            activateData = "{#p0}")
    public ApiResponse addDeviceTag(@Valid @RequestBody AddDeviceTagRequest request) throws ApiException {
        // @formatter:off
        DeviceTag deviceTag = new DeviceTag()
                .setName(request.getName())
                .setCreateBy(UserDetailsContext.getUserId());
        // @formatter:on
        deviceTag = this.deviceTagService.addDeviceTag(deviceTag);
        return ApiResponse.success(deviceTag);
    }

    /**
     * 删除设备标签
     *
     * @param tagId 设备标签ID {@link DeviceTag#getId()}
     * @return 已删除的设备标签 {@link DeviceTag}
     * @throws ApiException 删除过程中遇到的异常
     */
    @DeleteMapping(value = "/{tagId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.DeviceTag,
            objectId = "{#p0}",
            object = "{@deviceTagServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '设备标签删除成功' : '设备标签删除失败'}",
            activateData = "{#p0}")
    public ApiResponse deleteDeviceTag(@PathVariable("tagId") String tagId) throws ApiException {
        return ApiResponse.success(this.deviceTagService.deleteDeviceTag(tagId));
    }
}
