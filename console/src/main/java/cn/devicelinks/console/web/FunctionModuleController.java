package cn.devicelinks.console.web;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能模块接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/function/module")
@AllArgsConstructor
public class FunctionModuleController {

    private FunctionModuleService functionModuleService;

    /**
     * 获取功能模块列表
     *
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     * @return {@link cn.devicelinks.framework.common.pojos.FunctionModule}
     */
    @GetMapping
    public ApiResponse getFunctionModuleByPageable(@Valid PaginationQuery paginationQuery,
                                                   @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<FunctionModule> pageResult = this.functionModuleService.getFunctionModulesByPage(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
    }
}
