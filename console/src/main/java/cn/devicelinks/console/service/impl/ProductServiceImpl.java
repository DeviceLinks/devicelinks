package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.model.StatusCodeConstants;
import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.product.RegenerateKeySecretResponse;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.DeviceService;
import cn.devicelinks.console.service.ProductService;
import cn.devicelinks.framework.common.ProductStatus;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.repositorys.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TProduct.PRODUCT;

/**
 * 产品业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl extends BaseServiceImpl<Product, String, ProductRepository> implements ProductService {
    private final int PRODUCT_KEY_LENGTH = 30;
    private final int PRODUCT_SECRET_LENGTH = 50;
    private DeviceService deviceService;

    public ProductServiceImpl(ProductRepository repository, DeviceService deviceService) {
        super(repository);
        this.deviceService = deviceService;
    }

    @Override
    public PageResult<Product> getPageByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.getProductsByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public Product addProduct(Product product) {
        Product storedProduct = this.repository.selectOne(PRODUCT.NAME.eq(product.getName()), PRODUCT.DELETED.eq(Boolean.FALSE));
        if (storedProduct != null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_ALREADY_EXISTS, product.getName());
        }
        // @formatter:off
        product.setId(UUIDUtils.generateNoDelimiter())
                .setProductKey(StringUtils.getRandomString(PRODUCT_KEY_LENGTH))
                .setProductSecret(StringUtils.getRandomString(PRODUCT_SECRET_LENGTH))
                .setDeleted(Boolean.FALSE);
        // @formatter:on
        this.repository.insert(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        Product storedProduct = this.repository.selectOne(product.getId());
        if (storedProduct == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, product.getId());
        }
        if (!ProductStatus.Development.equals(storedProduct.getStatus())) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_IN_DEVELOPMENT_STATUS, product.getId());
        }
        storedProduct = this.repository.selectOne(
                ConditionGroup.withCondition(PRODUCT.ID.neq(product.getId()), PRODUCT.DELETED.eq(Boolean.FALSE)),
                ConditionGroup.withCondition(SqlFederationAway.OR, PRODUCT.NAME.eq(product.getName()), PRODUCT.PRODUCT_KEY.eq(product.getProductKey())));
        if (storedProduct != null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_ALREADY_EXISTS, product.getName());
        }
        this.repository.update(product);
        return product;
    }

    @Override
    public Product deleteProduct(String productId) {
        Product product = this.repository.selectOne(productId);
        if (product == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, productId);
        }
        if (ProductStatus.Published.equals(product.getStatus())) {
            throw new ApiException(StatusCodeConstants.PRODUCT_PUBLISHED, product.getName());
        }
        List<Device> deviceList = this.deviceService.selectByProductId(productId);
        if (!ObjectUtils.isEmpty(deviceList)) {
            throw new ApiException(StatusCodeConstants.PRODUCT_HAS_RELATED_DEVICES, product.getName());
        }
        product.setDeleted(Boolean.TRUE);
        this.repository.update(product);
        return product;
    }

    @Override
    public Product publishProduct(String productId) {
        Product product = this.repository.selectOne(productId);
        if (product == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, productId);
        }
        if (!ProductStatus.Development.equals(product.getStatus())) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_IN_DEVELOPMENT_STATUS, product.getId());
        }
        product.setStatus(ProductStatus.Published);
        this.repository.update(product);
        return product;
    }

    @Override
    public RegenerateKeySecretResponse regenerateKeySecret(String productId) {
        Product product = this.repository.selectOne(productId);
        if (product == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, productId);
        }
        String productKey = StringUtils.getRandomString(PRODUCT_KEY_LENGTH);
        String productSecret = StringUtils.getRandomString(PRODUCT_SECRET_LENGTH);
        product.setProductKey(productKey).setProductSecret(productSecret);
        this.repository.update(product);
        // @formatter:off
        return new RegenerateKeySecretResponse()
                .setProductId(productId)
                .setProductName(product.getName())
                .setProductKey(productKey)
                .setProductSecret(productSecret);
        // @formatter:on
    }
}
