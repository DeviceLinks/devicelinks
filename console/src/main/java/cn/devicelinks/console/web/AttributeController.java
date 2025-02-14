package cn.devicelinks.console.web;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 属性接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/attribute")
@AllArgsConstructor
public class AttributeController {

    private AttributeService attributeService;

    /**
     * 获取属性列表，支持分页和搜索功能。
     * <p>
     * 此方法根据提供的分页查询参数和搜索字段查询参数，返回符合条件的属性列表。
     *
     * @param paginationQuery  分页查询参数，包含当前页码和每页大小 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数，包含用于过滤属性的字段 {@link SearchFieldQuery}
     * @return 返回成功响应 {@link ApiResponse}，其中包含符合条件的属性列表。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @GetMapping
    public ApiResponse getAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                              @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<Attribute> pageResult = this.attributeService.getAttributesByPage(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
    }
}