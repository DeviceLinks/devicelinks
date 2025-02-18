package cn.devicelinks.console.service;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

/**
 * 产品业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ProductService extends BaseService<Product, String> {
    /**
     * 分页获取产品列表
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     * @return 产品列表 {@link PageResult}<Product>
     */
    PageResult<Product> getPageByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);
}
