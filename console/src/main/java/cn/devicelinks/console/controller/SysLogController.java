package cn.devicelinks.console.controller;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.service.system.SysLogService;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.entity.SysLog;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/log")
@AllArgsConstructor
public class SysLogController {

    private SysLogService sysLogService;

    /**
     * 获取分页系统日志列表
     * <p>
     * 该接口用于获取系统日志的分页列表。可以根据提供的分页参数和搜索条件进行筛选和排序。
     *
     * @param paginationQuery  分页查询参数，包括页码、每页大小和排序信息。必须是有效的 {@link PaginationQuery} 对象。
     * @param searchFieldQuery 用于过滤日志的搜索条件。可以包含各种特定字段的搜索参数。必须是有效的 {@link SearchFieldQuery} 对象。
     * @return 返回一个 {@link ApiResponse} 对象，包含符合给定条件的分页系统日志列表。
     * 响应中包括分页元数据和日志列表。
     * @throws ApiException 如果查询过程中发生错误，例如参数无效或数据库查询失败，将抛出 {@link ApiException} 异常。
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.Log)
    public ApiResponse<PageResult<SysLog>> getLogByPageable(@Valid PaginationQuery paginationQuery,
                                                            @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(this.sysLogService.getByPageable(paginationQuery, searchFieldQuery));
    }
}
