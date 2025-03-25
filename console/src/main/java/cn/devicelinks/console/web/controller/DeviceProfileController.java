package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceProfileService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
