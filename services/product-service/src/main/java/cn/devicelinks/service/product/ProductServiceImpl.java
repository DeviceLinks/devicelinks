package cn.devicelinks.service.product;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.response.RegenerateKeySecretResponse;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.ProductStatus;
import cn.devicelinks.common.utils.SecureRandomUtils;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.Product;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.cache.ProductCacheEvictEvent;
import cn.devicelinks.jdbc.cache.ProductCacheKey;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.jdbc.repository.DeviceRepository;
import cn.devicelinks.jdbc.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDevice.DEVICE;
import static cn.devicelinks.jdbc.tables.TProduct.PRODUCT;

/**
 * 产品业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl extends CacheBaseServiceImpl<Product, String, ProductRepository, ProductCacheKey, ProductCacheEvictEvent> implements ProductService {
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
    public void handleCacheEvictEvent(ProductCacheEvictEvent event) {
        Product savedProduct = event.getSavedProduct();
        if (savedProduct != null) {
            cache.put(ProductCacheKey.builder().productId(event.getProductId()).build(), savedProduct);
        } else {
            cache.evict(ProductCacheKey.builder().productId(event.getProductId()).build());
        }
    }

    @Override
    public PageResult<Product> getPageByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getProductsByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
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
        publishCacheEvictEvent(ProductCacheEvictEvent.builder().savedProduct(product).build());
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
        publishCacheEvictEvent(ProductCacheEvictEvent.builder().productId(product.getId()).build());
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
        publishCacheEvictEvent(ProductCacheEvictEvent.builder().productId(product.getId()).build());
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
        publishCacheEvictEvent(ProductCacheEvictEvent.builder().productId(product.getId()).build());
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
        publishCacheEvictEvent(ProductCacheEvictEvent.builder().productId(product.getId()).build());
        // @formatter:off
        return new RegenerateKeySecretResponse()
                .setProductId(productId)
                .setProductName(product.getName())
                .setProductKey(productKey)
                .setProductSecret(productSecret);
        // @formatter:on
    }
}
