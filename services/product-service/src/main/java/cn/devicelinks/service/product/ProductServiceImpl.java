package cn.devicelinks.service.product;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.query.PaginationQuery;
import cn.devicelinks.api.support.query.SearchFieldQuery;
import cn.devicelinks.api.support.response.RegenerateKeySecretResponse;
import cn.devicelinks.framework.common.ProductStatus;
import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.common.utils.SecureRandomUtils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.repositorys.DeviceRepository;
import cn.devicelinks.framework.jdbc.repositorys.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDevice.DEVICE;
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
    private final int PRODUCT_KEY_LENGTH = 32;
    private final int PRODUCT_SECRET_LENGTH = 64;
    private final DeviceRepository deviceRepository;
    private final FunctionModuleService functionModuleService;

    public ProductServiceImpl(ProductRepository repository, DeviceRepository deviceRepository, FunctionModuleService functionModuleService) {
        super(repository);
        this.deviceRepository = deviceRepository;
        this.functionModuleService = functionModuleService;
    }

    @Override
    public PageResult<Product> getPageByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.getProductsByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public Product addProduct(Product product, UserAuthorizedAddition authorizedAddition) {
        Product storedProduct = this.repository.selectOne(PRODUCT.NAME.eq(product.getName()), PRODUCT.DELETED.eq(Boolean.FALSE));
        if (storedProduct != null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_ALREADY_EXISTS, product.getName());
        }
        // @formatter:off
        product.setProductKey(SecureRandomUtils.generateRandomHex(PRODUCT_KEY_LENGTH))
                .setProductSecret(SecureRandomUtils.generateRandomHex(PRODUCT_SECRET_LENGTH));
        // @formatter:on
        this.repository.insert(product);
        // add default function module
        this.functionModuleService.addProductDefaultFunctionModule(product.getId(), authorizedAddition);
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
        List<Device> deviceList = this.deviceRepository.select(DEVICE.PRODUCT_ID.eq(productId), DEVICE.DELETED.eq(Boolean.FALSE));
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
        String productKey = SecureRandomUtils.generateRandomHex(PRODUCT_KEY_LENGTH);
        String productSecret = SecureRandomUtils.generateRandomHex(PRODUCT_SECRET_LENGTH);
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
