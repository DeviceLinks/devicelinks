package cn.devicelinks.console.controller;

import cn.devicelinks.api.model.request.*;
import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import cn.devicelinks.service.device.DeviceProfileService;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.converter.DeviceProfileConverter;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.framework.common.web.search.annotation.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.common.pojos.DeviceProfileLogAddition;
import cn.devicelinks.framework.common.pojos.DeviceProfileProvisionAddition;
import cn.devicelinks.framework.common.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 设备配置文件接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device-profile")
@AllArgsConstructor
public class DeviceProfileController {

    private DeviceProfileService deviceProfileService;

    /**
     * 分页获取设备配置列表
     *
     * @param paginationQuery  分页查询参数实体 {@link PaginationQuery}
     * @param searchFieldQuery 检索字段参数实体 {@link SearchFieldQuery}
     * @return 设备配置列表
     * @throws ApiException 查询过程中遇到的异常
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DeviceProfile)
    public ApiResponse<PageResult<DeviceProfile>> getDeviceProfileListByPageable(@Valid PaginationQuery paginationQuery,
                                                                                 @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<DeviceProfile> pageResult = this.deviceProfileService.getDeviceProfileListByPageable(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
    }

    /**
     * 获取设备配置文件
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @return 设备配置文件信息 {@link DeviceProfile}
     * @throws ApiException 查询过程中遇到的异常
     */
    @GetMapping(value = "/{profileId}")
    public ApiResponse<DeviceProfile> getDeviceProfile(@PathVariable("profileId") String profileId) throws ApiException {
        DeviceProfile deviceProfile = this.deviceProfileService.selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }
        return ApiResponse.success(deviceProfile);
    }

    /**
     * 添加设备配置文件
     *
     * @param request 添加配置文件请求实体参数 {@link AddDeviceProfileRequest}
     * @return 已添加的设备配置文件
     * @throws ApiException 添加设备配置文件过程中遇到的异常
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#executionSucceed? #result.data.id : #p0.name}",
            msg = "{#executionSucceed? '设备配置文件添加成功' : '设备配置文件添加失败'}",
            activateData = "{#p0}")
    public ApiResponse<DeviceProfile> addDeviceProfile(@Valid @RequestBody AddDeviceProfileRequest request) throws ApiException {
        UserAuthorizedAddition authorizedAddition = UserDetailsContext.getUserAddition();
        DeviceProfile addedDeviceProfile = this.deviceProfileService.addDeviceProfile(request, authorizedAddition);
        return ApiResponse.success(addedDeviceProfile);
    }

    /**
     * 更新设备配置文件
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param request   更新设备配置文件请求实体参数 {@link UpdateDeviceProfileRequest}
     * @return 更新后的设备配置文件
     * @throws ApiException 更新过程中遇到的业务逻辑异常
     */
    @PostMapping(value = "/{profileId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#executionSucceed? #result.data.id : #p0}",
            object = "{@deviceProfileServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '设备配置文件更新成功' : '设备配置文件更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<DeviceProfile> updateDeviceProfile(@PathVariable("profileId") String profileId,
                                                          @Valid @RequestBody UpdateDeviceProfileRequest request) throws ApiException {
        DeviceProfile deviceProfile = this.deviceProfileService.selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }
        DeviceProfileConverter.INSTANCE.fromUpdateDeviceProfileRequest(request, deviceProfile);
        deviceProfile = this.deviceProfileService.updateDeviceProfile(deviceProfile);
        return ApiResponse.success(deviceProfile);
    }

    /**
     * 更新基础信息
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param request   更新设备配置文件基本信息 {@link UpdateDeviceProfileBasicInfoRequest}
     * @return 更新后的基本信息
     * @throws ApiException 更新过程中遇到的业务逻辑异常
     */
    @PutMapping(value = "/{profileId}/basic-info")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#p0}",
            object = "{@deviceProfileServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '设备配置文件基础信息更新成功' : '设备配置文件基础信息更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<DeviceProfile> updateDeviceProfileBasicInfo(@PathVariable("profileId") String profileId,
                                                                   @Valid @RequestBody UpdateDeviceProfileBasicInfoRequest request) throws ApiException {
        DeviceProfile deviceProfile = this.deviceProfileService.updateDeviceProfileBasicInfo(profileId, request);
        return ApiResponse.success(deviceProfile);
    }

    /**
     * 更新扩展配置
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param request   更新设备配置文件扩展配置请求实体 {@link UpdateDeviceProfileExtensionRequest}
     * @return 更新后的扩展配置
     * @throws ApiException 更新过程中遇到的业务逻辑异常
     */
    @PutMapping(value = "/{profileId}/extension")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#p0}",
            object = "{@deviceProfileServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '设备配置文件扩展配置更新成功' : '设备配置文件扩展配置更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<Map<String, Object>> updateDeviceProfileExtension(@PathVariable("profileId") String profileId,
                                                                         @Valid @RequestBody UpdateDeviceProfileExtensionRequest request) throws ApiException {
        Map<String, Object> extensionJson = this.deviceProfileService.updateDeviceProfileExtension(profileId, request.getExtension());
        return ApiResponse.success(extensionJson);
    }

    /**
     * 更新日志附加配置
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param request   更新日志附加信息请求实体 {@link UpdateDeviceProfileLogAdditionRequest}
     * @return 更新后的日志附加信息
     * @throws ApiException 更新过程中遇到的业务逻辑异常
     */
    @PutMapping(value = "/{profileId}/log-addition")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#p0}",
            object = "{@deviceProfileServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '设备配置文件日志附加配置更新成功' : '设备配置文件日志附加配置更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<DeviceProfileLogAddition> updateDeviceProfileLogAddition(@PathVariable("profileId") String profileId,
                                                                                @Valid @RequestBody UpdateDeviceProfileLogAdditionRequest request) throws ApiException {
        DeviceProfileLogAddition logAddition = this.deviceProfileService.updateDeviceProfileLogAddition(profileId, request.getLogAddition());
        return ApiResponse.success(logAddition);
    }

    /**
     * 更新设备配置文件的预配置附加信息。
     * <p>
     * 该函数通过HTTP PUT请求接收设备配置文件的ID和更新请求体，调用服务层方法更新设备配置文件的预配置附加信息，
     * 并返回更新后的预配置附加信息。
     *
     * @param profileId 设备配置文件的唯一标识符，通过URL路径变量传递。
     * @param request   包含预配置附加信息更新请求的请求体，通过请求体传递。
     * @return 返回一个包含更新后的预配置附加信息的ApiResponse对象。
     * @throws ApiException 如果更新过程中发生错误，则抛出ApiException异常。
     */
    @PutMapping(value = "/{profileId}/provision-addition")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#p0}",
            object = "{@deviceProfileServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '设备配置文件预配置更新成功' : '设备配置文件预配置更新失败'}",
            activateData = "{#p1}"
    )
    public ApiResponse<DeviceProfileProvisionAddition> updateDeviceProfileProvisionAddition(@PathVariable("profileId") String profileId,
                                                                                            @Valid @RequestBody UpdateDeviceProfileProvisionAdditionRequest request) throws ApiException {
        DeviceProfileProvisionAddition provisionAddition = this.deviceProfileService.updateDeviceProfileProvisionAddition(profileId, request.getProvisionAddition());
        return ApiResponse.success(provisionAddition);
    }

    /**
     * 删除设备配置文件
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @return 已删除的设备配置文件 {@link DeviceProfile}
     * @throws ApiException 删除过程中遇到的异常
     */
    @DeleteMapping(value = "/{profileId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#executionSucceed? #result.data.id : #p0}",
            object = "{@deviceProfileServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '设备配置文件删除成功' : '设备配置文件删除失败'}",
            activateData = "{#p0}")
    public ApiResponse<DeviceProfile> deleteDeviceProfile(@PathVariable("profileId") String profileId) throws ApiException {
        DeviceProfile deletedDeviceProfile = this.deviceProfileService.deleteDeviceProfile(profileId);
        return ApiResponse.success(deletedDeviceProfile);
    }

    /**
     * 将设备配置文件批量设置给设备
     *
     * @param profileId 配置文件ID {@link DeviceProfile#getId()}
     * @param request   批量设置请求参数实体 {@link BatchSetDeviceProfileRequest}
     * @return 统一响应实体
     * @throws ApiException 批量设置过程中遇到的异常
     */
    @PostMapping(value = "/{profileId}/batch-set")
    @OperationLog(action = LogAction.Bind,
            objectType = LogObjectType.DeviceProfile,
            objectId = "{#p0}",
            msg = "{#executionSucceed? '批量绑定成功' : '批量绑定失败'}",
            activateData = "{#p1}")
    public ApiResponse<Object> batchSetDeviceProfile(@PathVariable("profileId") String profileId,
                                                     @Valid @RequestBody BatchSetDeviceProfileRequest request) throws ApiException {
        this.deviceProfileService.batchSet(profileId, request);
        return ApiResponse.success();
    }
}
