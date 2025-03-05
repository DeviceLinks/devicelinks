package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.DeviceAttributeDesiredService;
import cn.devicelinks.console.service.DeviceAttributeReportedService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
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
@RequestMapping(value = "/api/device/attribute")
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
    @PostMapping(value = "/reported/filter")
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
    @PostMapping(value = "/desired/filter")
    public ApiResponse getDesiredAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                                     @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(desiredAttributeService.getByPageable(searchFieldQuery, paginationQuery));
    }
}
