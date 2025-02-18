package cn.devicelinks.console.web;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.ProductService;
import cn.devicelinks.framework.common.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 产品接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 获取分页产品列表，支持可选的搜索条件。
     * <p>
     * 该接口允许客户端获取分页的产品列表。结果可以根据提供的搜索条件进行过滤和排序。
     *
     * @param paginationQuery  分页查询参数，包括页码、每页大小和排序信息。必须是有效的 {@link PaginationQuery} 对象。
     * @param searchFieldQuery 用于过滤产品的搜索条件。可以包含各种特定字段的搜索参数。必须是有效的 {@link SearchFieldQuery} 对象。
     * @return 返回一个 {@link ApiResponse} 对象，包含符合给定条件的分页产品列表。
     * 响应中包括分页元数据和产品列表。
     */
    @PostMapping(value = "/filter")
    public ApiResponse getProductByPageable(@Valid PaginationQuery paginationQuery, @Valid @RequestBody SearchFieldQuery searchFieldQuery) {
        return ApiResponse.success(this.productService.getPageByPageable(paginationQuery, searchFieldQuery));
    }
}