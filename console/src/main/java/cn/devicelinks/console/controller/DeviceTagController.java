package cn.devicelinks.console.controller;

import cn.devicelinks.api.model.request.AddDeviceTagRequest;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.entity.DeviceTag;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.service.device.DeviceTagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<List<DeviceTag>> getDeviceTagList(@Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        return ApiResponse.success(this.deviceTagService.selectDeviceTagList(searchFieldConditionList));
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
    public ApiResponse<DeviceTag> addDeviceTag(@Valid @RequestBody AddDeviceTagRequest request) throws ApiException {
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
    public ApiResponse<DeviceTag> deleteDeviceTag(@PathVariable("tagId") String tagId) throws ApiException {
        return ApiResponse.success(this.deviceTagService.deleteDeviceTag(tagId));
    }
}
