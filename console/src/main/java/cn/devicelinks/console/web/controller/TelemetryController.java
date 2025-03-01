package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.TelemetryService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Telemetry;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 遥测数据接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/telemetry")
@AllArgsConstructor
public class TelemetryController {

    private TelemetryService telemetryService;

    /**
     * 获取遥测数据列表，支持分页和搜索功能。
     * <p>
     * 此方法根据提供的分页查询参数和搜索字段查询参数，返回符合条件的遥测数据列表。
     *
     * @param paginationQuery  分页查询参数，包含当前页码和每页大小 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数，包含用于过滤遥测数据的字段 {@link SearchFieldQuery}
     * @return 返回成功响应 {@link ApiResponse}，其中包含符合条件的遥测数据列表。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @PostMapping(value = "/filter")
    public ApiResponse getTelemetryByPageable(@Valid PaginationQuery paginationQuery,
                                              @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<Telemetry> pageResult = this.telemetryService.getTelemetryByPage(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
    }
}
