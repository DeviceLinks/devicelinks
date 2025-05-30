package cn.devicelinks.service.product;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.response.RegenerateKeySecretResponse;
import cn.devicelinks.common.ProductStatus;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.entity.Product;
import cn.devicelinks.jdbc.BaseService;
import cn.devicelinks.jdbc.core.page.PageResult;

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

    /**
     * 新增产品
     * <p>
     * 该方法用于新增一个产品。传入的产品对象将被持久化到数据库中，并返回已添加的产品信息。
     *
     * @param product            包含产品详细信息的对象。必须是有效的 {@link Product} 对象。
     * @param authorizedAddition 当前用户授权附加信息
     * @return 返回已添加的产品对象 {@link Product}，包含新增后的完整信息。
     */
    Product addProduct(Product product, UserAuthorizedAddition authorizedAddition);

    /**
     * 更新产品
     * <p>
     * 该方法通过产品 ID 更新特定产品的信息。传入的产品对象的 ID 必须与需要更新的产品 ID 匹配，
     * 并使用请求体中的新数据替换原有信息。
     *
     * @param product 包含产品详细信息的对象。必须是有效的 {@link Product} 对象。
     * @return 返回已更新的产品对象 {@link Product}，包含更新后的完整信息。
     */
    Product updateProduct(Product product);

    /**
     * 删除产品
     *
     * @param productId 产品ID {@link Product#getId()}
     * @return 已经删除的产品信息 {@link Product}
     */
    Product deleteProduct(String productId);

    /**
     * 发布产品
     * <p>
     * 仅处于开发状态{@link ProductStatus#Development}才允许发布产品
     *
     * @param productId 产品 ID {@link Product#getId()}
     * @return 已发布的产品信息 {@link Product}
     */
    Product publishProduct(String productId);

    /**
     * 重新生成KeySecret
     *
     * @param productId 产品 ID {@link Product#getId()}
     * @return 已重新生成的 KeySecret {@link Product}
     */
    RegenerateKeySecretResponse regenerateKeySecret(String productId);

    /**
     * 根据产品Key查询产品基本信息
     *
     * @param productKey {@link Product#getProductKey()}
     * @return {@link Product}
     */
    Product selectByKey(String productKey);
}