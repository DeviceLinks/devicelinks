package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceProfileService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceProfileRequest;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 设备配置文件接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/device/profile")
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
    public ApiResponse getDeviceProfileListByPageable(@Valid PaginationQuery paginationQuery,
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
    public ApiResponse getDeviceProfile(@PathVariable("profileId") String profileId) throws ApiException {
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
    public ApiResponse addDeviceProfile(@Valid @RequestBody AddDeviceProfileRequest request) throws ApiException {
        DeviceProfile addedDeviceProfile = this.deviceProfileService.addDeviceProfile(request);
        return ApiResponse.success(addedDeviceProfile);
    }
}
